package com.prm.productsale.repository;

import com.prm.productsale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {
  Optional<UserEntity> findByEmail(String email);
  Boolean existsByEmail(String email);
}
