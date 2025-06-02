package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderResponse toOrderResponse(OrderEntity orderEntity);
    List<OrderResponse> toOrderResponseList(List<OrderEntity> orderEntities);
}
