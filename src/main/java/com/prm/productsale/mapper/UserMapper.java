package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Bỏ qua password
  UserResponse toUserResponse(UserEntity user);
}
