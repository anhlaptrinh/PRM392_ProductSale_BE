package com.prm.productsale.repository;

import com.prm.productsale.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartEntity,Integer> {
    Optional<CartEntity> findByUserId(int userId);
    List<CartEntity> findByUser_IdAndStatus(int userId, String status);
}
