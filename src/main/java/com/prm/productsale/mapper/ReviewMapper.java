package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.ReviewRequest;
import com.prm.productsale.dto.response.ReviewResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

  @Mapping(source = "product.id", target = "productID")
  @Mapping(source = "user.id", target = "userID")
  @Mapping(source = "user.username", target = "username")
  ReviewResponse toReviewResponse(ReviewEntity entity);

  List<ReviewResponse> toListReviewResponse(List<ReviewEntity> entities);

  @Mapping(target = "reviewID", ignore = true)
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "helpfulCount", constant = "0")
  @Mapping(target = "product", source = "product")
  @Mapping(target = "user", source = "user")
  ReviewEntity toReviewEntity(ReviewRequest request, ProductEntity product, UserEntity user);
}
