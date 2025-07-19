package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.ReviewReplyRequest;
import com.prm.productsale.dto.response.ReviewReplyResponse;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.ReviewReplyEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.utils.CommonMapUtils;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewReplyMapper extends CommonMapUtils {

  @Mapping(source = "reviewID", target = "reviewID")
  @Mapping(source = "repliedBy", target = "repliedBy")
  @Mapping(source = "replyText", target = "replyText")
  @Mapping(source = "repliedAt", target = "repliedAt", qualifiedByName = "formatDateTimeToString")
  @Mapping(source = "replyID", target = "replyID")
  ReviewReplyResponse toResponse(ReviewReplyEntity entity);

  List<ReviewReplyResponse> toListResponse(List<ReviewReplyEntity> entities);

  @Mapping(target = "replyID", ignore = true)
  @Mapping(target = "repliedAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(source = "review.reviewID", target = "reviewID")
  @Mapping(source = "user.id", target = "repliedBy")
  @Mapping(source = "request.replyText", target = "replyText")
  ReviewReplyEntity toEntity(ReviewReplyRequest request, ReviewEntity review, UserEntity user);
}
