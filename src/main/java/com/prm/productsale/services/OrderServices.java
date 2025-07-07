package com.prm.productsale.services;

import com.prm.productsale.dto.response.OrderResponse;

import java.util.List;

public interface OrderServices {
    List<OrderResponse> getAll();
    List<OrderResponse> findByStatus(String status);
    void editStatus(int orderId,String status);
}
