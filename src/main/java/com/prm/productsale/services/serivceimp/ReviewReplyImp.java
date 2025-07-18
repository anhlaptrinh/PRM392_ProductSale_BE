package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ReviewReplyRequest;
import com.prm.productsale.dto.response.ReviewReplyResponse;
import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.ReviewReplyEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.mapper.ReviewReplyMapper;
import com.prm.productsale.mapper.UserMapper;
import com.prm.productsale.repository.ReviewReplyRepo;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.services.ReviewReplyServices;
import com.prm.productsale.validator.ReviewReplyValidator;
import com.prm.productsale.validator.ReviewValidator;
import com.prm.productsale.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewReplyImp implements ReviewReplyServices {

  @Autowired private ReviewReplyRepo reviewReplyRepo;
  @Autowired private UserRepo userRepo;
  @Autowired private ReviewReplyMapper replyMapper;
  @Autowired private ReviewReplyValidator replyValidator;
  @Autowired private UserValidator userValidator;
  @Autowired private ReviewValidator reviewValidator;
  @Autowired private UserServicesImp userServicesImp;

  @Override
  public ReviewReplyResponse createReply(int reviewId, ReviewReplyRequest request) {
    ReviewEntity review = reviewValidator.validateReviewExist(reviewId);

    UserResponse userResponse = userServicesImp.getMyInfo();
    UserEntity user = userValidator.validateExist(userResponse.getId());

    ReviewReplyEntity entity = replyMapper.toEntity(request, review, user);

    reviewReplyRepo.save(entity);

    ReviewReplyResponse response = replyMapper.toResponse(entity);
    response.setUsername(user.getUsername());
    return response;
  }

  @Override
  public List<ReviewReplyResponse> getRepliesByReviewId(int reviewId) {
    ReviewEntity review = reviewValidator.validateReviewExist(reviewId);

    List<ReviewReplyEntity> entities = reviewReplyRepo.findByReviewID(reviewId);
    List<ReviewReplyResponse> responses = replyMapper.toListResponse(entities);

    // Đảm bảo username không bị null nếu user đã bị xóa
    for (ReviewReplyResponse resp : responses) {
      UserEntity user = userValidator.validateExist(resp.getRepliedBy());
      resp.setUsername(user.getUsername());
    }
    return responses;
  }

  @Override
  public void deleteReplyById(int replyID) {
    ReviewReplyEntity replyEntity = replyValidator.validateExistAndNotDeleted(replyID);
    reviewReplyRepo.delete(replyEntity);
  }
}
