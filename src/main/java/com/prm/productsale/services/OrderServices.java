package com.prm.productsale.services;

import com.prm.productsale.dto.request.OrderRequest;
import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.OrderEntity;

import java.util.List;

public interface OrderServices {
    List<OrderResponse> getAll();
    List<OrderResponse> findByStatus(String status);
    void editStatus(int orderId,String status);
    OrderEntity createOrder(OrderRequest request);
    OrderEntity getOrder(int id);
}
