package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.OrderHistoryResponse;
import com.prm.productsale.entity.OrderEntity;

import java.time.format.DateTimeFormatter;

public class OrderHistoryMapper {
    public static OrderHistoryResponse toResponse(OrderEntity entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new OrderHistoryResponse(
                entity.getId(),
                entity.getOrderDate().format(formatter),
                entity.getCart().getTotal(),
                entity.getOrderStatus() // PENDING, SHIPPING, ARRIVED, CANCELLED
        );
    }
}
