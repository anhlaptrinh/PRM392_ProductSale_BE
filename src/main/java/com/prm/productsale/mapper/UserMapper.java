package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "string")
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  // =========================
  // 1. Entity -> Response Mapping
  // =========================

  // Bỏ qua password
  UserResponse toUserResponse(UserEntity user);

  List<UserResponse> toListUserResponse(List<UserEntity> userEntityList);
}
