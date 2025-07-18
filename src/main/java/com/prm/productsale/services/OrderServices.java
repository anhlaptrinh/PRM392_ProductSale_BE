package com.prm.productsale.services;

import com.prm.productsale.dto.request.OrderRequest;
import com.prm.productsale.dto.request.ReorderRequest;
import com.prm.productsale.dto.response.OrderHistoryResponse;
import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.entity.OrderEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderServices {
    List<OrderResponse> getAll();
    List<OrderResponse> findByStatus(String status);
    void editStatus(int orderId,String status);
    OrderEntity createOrder(OrderRequest request);
    OrderEntity getOrder(int id);
    void markCartInactive(CartEntity cart);
    List<OrderHistoryResponse> getOrdersByUserId(int userId);
    List<CartItemEntity> getCartItemsByOrderId(int orderId);
    void updateOrder(int orderId);
    void reorder(ReorderRequest request);
}
