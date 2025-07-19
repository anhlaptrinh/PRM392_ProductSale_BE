package com.prm.productsale.repository;

import com.prm.productsale.entity.ReviewEntity;
import com.prm.productsale.entity.ReviewVoteEntity;
import com.prm.productsale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewVoteRepo extends JpaRepository<ReviewVoteEntity, Integer> {
  Optional<ReviewVoteEntity> findByReviewAndUser(ReviewEntity review, UserEntity user);
}
