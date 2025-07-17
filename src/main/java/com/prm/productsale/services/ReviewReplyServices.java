package com.prm.productsale.services;

import com.prm.productsale.dto.request.ReviewReplyRequest;
import com.prm.productsale.dto.response.ReviewReplyResponse;

import java.util.List;

public interface ReviewReplyServices {
  ReviewReplyResponse createReply(int reviewId, ReviewReplyRequest request);
  List<ReviewReplyResponse> getRepliesByReviewId(int reviewId);
}
