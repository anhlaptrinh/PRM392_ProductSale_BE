package com.prm.productsale.repository;

import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity, Integer> {
  List<ReviewEntity> findByProduct_IdAndIsDeletedFalseOrderByCreatedAtDesc(int productId);
  boolean existsByProductAndUserAndIsDeletedFalse(ProductEntity product, UserEntity user);
  Optional<ReviewEntity> findByProductAndUserAndIsDeletedTrue(ProductEntity product, UserEntity user);
}
