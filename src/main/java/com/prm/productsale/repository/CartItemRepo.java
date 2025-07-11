package com.prm.productsale.repository;

import com.prm.productsale.entity.CartItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItemEntity,Integer> {
    Page<CartItemEntity> findByCartId(int cartId, Pageable pageable);
    List<CartItemEntity> findByCartId(int cartId);
    Optional<CartItemEntity> findByCartIdAndProductId(int cartId, int productId);

}
