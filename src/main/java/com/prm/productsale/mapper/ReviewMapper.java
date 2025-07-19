package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.response.ReviewResponse;
import com.prm.productsale.dto.response.ReviewVoteResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.ReviewVoteEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.utils.CommonMapUtils;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends CommonMapUtils {

  @Mapping(source = "product.id", target = "productID")
  @Mapping(source = "user.id", target = "userID")
  @Mapping(source = "user.username", target = "username")
  @Mapping(source = "user.email", target = "email")
  @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "formatDateTimeToString")
  ReviewResponse toReviewResponse(ReviewEntity entity);

  List<ReviewResponse> toListReviewResponse(List<ReviewEntity> entities);

  @Mapping(target = "reviewID", ignore = true)
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "helpfulCount", constant = "0")
  @Mapping(target = "product", source = "product")
  @Mapping(target = "user", source = "user")
  ReviewEntity toReviewEntity(ReviewRequest request, ProductEntity product, UserEntity user);

  // Vote
  @Mapping(source = "review.reviewID", target = "reviewID")
  @Mapping(source = "user.id", target = "userID")
  @Mapping(source = "user.email", target = "email")
  ReviewVoteResponse toVoteResponse(ReviewVoteEntity vote);
  List<ReviewVoteResponse> toVoteResponseList(List<ReviewVoteEntity> votes);

}

