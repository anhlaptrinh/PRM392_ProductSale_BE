package com.prm.productsale.repository;

import com.prm.productsale.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity,Integer> {
    PaymentEntity findByOrderID(int orderID);
}
