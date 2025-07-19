package com.prm.productsale.validator;

import com.prm.productsale.entity.ReviewReplyEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.ReviewReplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewReplyValidator {

  @Autowired
  private ReviewReplyRepo reviewReplyRepo;

  public ReviewReplyEntity validateExistAndNotDeleted(int replyId) {
    ReviewReplyEntity reply = reviewReplyRepo.findById(replyId)
            .orElseThrow(() -> new AppException(ErrorCode.REPLY_NOT_FOUND));
    return reply;
  }
}
