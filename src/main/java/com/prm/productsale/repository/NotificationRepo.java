package com.prm.productsale.repository;

import com.prm.productsale.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prm.productsale.entity.UserEntity;

import java.util.List;
@Repository
 public interface NotificationRepo extends JpaRepository<NotificationEntity, Integer> {
    List<NotificationEntity> findByUser(UserEntity user);
}