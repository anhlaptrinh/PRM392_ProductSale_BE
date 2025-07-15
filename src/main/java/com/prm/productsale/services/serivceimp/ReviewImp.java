package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.request.VoteRequest;
import com.prm.productsale.dto.response.ReviewResponse;
import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.ReviewVoteEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ReviewMapper;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.repository.ReviewRepo;
import com.prm.productsale.repository.ReviewVoteRepo;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.services.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewImp implements ReviewServices {

  @Autowired
  private ReviewRepo reviewRepo;

  @Autowired
  private ProductRepo productRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ReviewVoteRepo voteRepo;

  @Autowired
  private ReviewMapper reviewMapper;

  @Autowired
  private UserServicesImp userServicesImp;

  // =========================
  // 1. Các method "Read" / "List" (GET)
  // =========================

  @Override
  public List<ReviewResponse> getByProductId(int productID) {
    List<ReviewEntity> reviews = reviewRepo.findByProduct_IdOrderByCreatedAtDesc(productID);
    return reviewMapper.toListReviewResponse(reviews);
  }

  // =========================
  // 2. Các method "Create" (POST)
  // =========================

  @Override
  public ReviewResponse createReview(ReviewRequest request) {
    if (request.getRating() < 1 || request.getRating() > 5) {
      throw new AppException(ErrorCode.RATING_OUT_OF_RANGE);
    }

    ProductEntity product = productRepo.findById(request.getProductID())
            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));

    UserResponse userRes = userServicesImp.getMyInfo();
    UserEntity user = userRepo.findById(userRes.getId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

    boolean exists = reviewRepo.existsByProductAndUser(product, user);
    if (exists) {
      throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
    }

    ReviewEntity entity = reviewMapper.toReviewEntity(request, product, user);
    ReviewEntity saved = reviewRepo.save(entity);

    return reviewMapper.toReviewResponse(saved);
  }

  @Override
  public void vote(VoteRequest request) {
    if (!request.getVoteType().equals("up") && !request.getVoteType().equals("down")) {
      throw new AppException(ErrorCode.INVALID_VOTE_TYPE);
    }

    ReviewEntity review = reviewRepo.findById(request.getReviewID())
            .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    checkIfReviewDeleted(review);

    UserEntity user = userRepo.findById(userServicesImp.getMyInfo().getId())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

    Optional<ReviewVoteEntity> existing = voteRepo.findByReviewAndUser(review, user);
    if (existing.isPresent()) throw new AppException(ErrorCode.REVIEW_ALREADY_VOTED);

    ReviewVoteEntity vote = new ReviewVoteEntity();
    vote.setReview(review);
    vote.setUser(user);
    vote.setVoteType(request.getVoteType());
    voteRepo.save(vote);

    // Update HelpfulCount
    if ("up".equals(request.getVoteType())) {
      review.setHelpfulCount(review.getHelpfulCount() + 1);
    } else {
      review.setHelpfulCount(Math.max(0, review.getHelpfulCount() - 1));
    }
    reviewRepo.save(review);
  }


  // =========================
  // 4. Các method "Delete" (DELETE) hoặc đặc biệt
  // =========================

  @Override
  public void deleteOwnReview(int reviewID) {
    UserResponse userResponse = userServicesImp.getMyInfo();

    ReviewEntity reviewEntity = reviewRepo.findById(reviewID)
            .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

    checkIfReviewDeleted(reviewEntity);

    if (userResponse.getId() != reviewEntity.getUser().getId()) {
      throw new AppException(ErrorCode.FORBIDDEN);
    }

    reviewEntity.setDeleted(true);
    reviewRepo.save(reviewEntity);
  }

  // =========================
  // 5. Các method "Utility" (private helpers)
  // =========================

  private void checkIfReviewDeleted(ReviewEntity review) {
    if (review.isDeleted()) {
      throw new AppException(ErrorCode.REVIEW_ALREADY_DELETED);
    }
  }
}
