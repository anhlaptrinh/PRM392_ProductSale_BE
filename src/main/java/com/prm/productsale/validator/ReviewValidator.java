package com.prm.productsale.validator;

import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewValidator {

  @Autowired
  private ReviewRepo reviewRepo;

  public ReviewEntity validateReviewExist(int reviewId) {
    return reviewRepo.findById(reviewId)
            .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
  }

  public ReviewEntity validateExistAndNotDeleted(int reviewId) {
    ReviewEntity review = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    if (review.isDeleted()) {
      throw new AppException(ErrorCode.REVIEW_ALREADY_DELETED);
    }
    return review;
  }

  public void validateRatingRange(int rating) {
    if (rating < 1 || rating > 5) {
      throw new AppException(ErrorCode.RATING_OUT_OF_RANGE);
    }
  }

  public void validateVoteType(String type) {
    if (!"up".equals(type) && !"down".equals(type)) {
      throw new AppException(ErrorCode.INVALID_VOTE_TYPE);
    }
  }
}
