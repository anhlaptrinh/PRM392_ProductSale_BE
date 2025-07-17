package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.request.VoteRequest;
import com.prm.productsale.dto.response.ReviewResponse;
import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.*;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ReviewMapper;
import com.prm.productsale.repository.ReviewRepo;
import com.prm.productsale.repository.ReviewVoteRepo;
import com.prm.productsale.services.ReviewServices;
import com.prm.productsale.validator.ProductValidator;
import com.prm.productsale.validator.ReviewValidator;
import com.prm.productsale.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewImp implements ReviewServices {

  @Autowired private ReviewRepo reviewRepo;
  @Autowired private ReviewVoteRepo voteRepo;
  @Autowired private ReviewMapper reviewMapper;
  @Autowired private UserServicesImp userServicesImp;
  @Autowired private UserValidator userValidator;
  @Autowired private ReviewValidator reviewValidator;
  @Autowired private ProductValidator productValidator;

  @Override
  public List<ReviewResponse> getByProductId(int productID) {
    return reviewMapper.toListReviewResponse(
            reviewRepo.findByProduct_IdAndIsDeletedFalseOrderByCreatedAtDesc(productID));
  }

  @Override
  public ReviewResponse createReview(ReviewRequest request) {
    reviewValidator.validateRatingRange(request.getRating());

    ProductEntity product = productValidator.validateExist(request.getProductID());

    UserResponse userRes = userServicesImp.getMyInfo();
    UserEntity user = userValidator.validateExist(userRes.getId());

    if (reviewRepo.existsByProductAndUser(product, user)) {
      throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
    }

    ReviewEntity entity = reviewMapper.toReviewEntity(request, product, user);
    return reviewMapper.toReviewResponse(reviewRepo.save(entity));
  }

  @Override
  public void vote(VoteRequest request) {
    reviewValidator.validateVoteType(request.getVoteType());
    ReviewEntity review = reviewValidator.validateExistAndNotDeleted(request.getReviewID());
    UserEntity user = userValidator.validateExist(userServicesImp.getMyInfo().getId());

    if (voteRepo.findByReviewAndUser(review, user).isPresent()) {
      throw new AppException(ErrorCode.REVIEW_ALREADY_VOTED);
    }

    ReviewVoteEntity vote = new ReviewVoteEntity();
    vote.setReview(review);
    vote.setUser(user);
    vote.setVoteType(request.getVoteType());
    voteRepo.save(vote);

    if ("up".equals(request.getVoteType())) {
      review.setHelpfulCount(review.getHelpfulCount() + 1);
    } else {
      review.setHelpfulCount(Math.max(0, review.getHelpfulCount() - 1));
    }

    reviewRepo.save(review);
  }

  @Override
  public void undoVote(int reviewId) {
    ReviewEntity review = reviewValidator.validateExistAndNotDeleted(reviewId);
    UserEntity user = userValidator.validateExist(userServicesImp.getMyInfo().getId());

    ReviewVoteEntity vote = voteRepo.findByReviewAndUser(review, user)
            .orElseThrow(() -> new AppException(ErrorCode.VOTE_NOT_FOUND));

    voteRepo.delete(vote);

    if ("up".equalsIgnoreCase(vote.getVoteType())) {
      review.setHelpfulCount(Math.max(0, review.getHelpfulCount() - 1));
      reviewRepo.save(review);
    }
  }

  @Override
  public void deleteOwnReview(int reviewID) {
    ReviewEntity review = reviewValidator.validateExistAndNotDeleted(reviewID);
    int currentUserId = userServicesImp.getMyInfo().getId();

    if (review.getUser().getId() != currentUserId) {
      throw new AppException(ErrorCode.FORBIDDEN);
    }

    review.setDeleted(true);
    reviewRepo.save(review);
  }
}
