package com.prm.productsale.services;

import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.request.VoteRequest;
import com.prm.productsale.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewServices {
  List<ReviewResponse> getByProductId(int productId);
  ReviewResponse createReview(ReviewRequest request);
  void vote(VoteRequest request);
  void deleteOwnReview(int reviewID);
  void undoVote(int reviewID);
}
