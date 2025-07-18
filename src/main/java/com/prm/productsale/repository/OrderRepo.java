package com.prm.productsale.repository;

import com.prm.productsale.dto.response.OrderHistoryResponse;
import com.prm.productsale.dto.response.OrderResponse;
import com.prm.productsale.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findByOrderStatus(String orderStatus);
    List<OrderEntity> findByUserId(int userId);
}
