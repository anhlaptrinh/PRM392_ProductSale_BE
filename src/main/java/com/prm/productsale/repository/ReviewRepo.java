package com.prm.productsale.repository;

import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<ReviewEntity, Integer> {
  List<ReviewEntity> findByProduct_IdAndIsDeletedFalseOrderByCreatedAtDesc(int productId);
  boolean existsByProductAndUser(ProductEntity product, UserEntity user);
}
