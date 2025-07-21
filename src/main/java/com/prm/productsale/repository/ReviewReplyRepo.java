package com.prm.productsale.repository;

import com.prm.productsale.entity.ReviewReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewReplyRepo extends JpaRepository<ReviewReplyEntity, Integer> {
  List<ReviewReplyEntity> findByReviewID(int reviewId);
}
