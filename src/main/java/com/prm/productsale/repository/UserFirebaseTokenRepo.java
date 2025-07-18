package com.prm.productsale.repository;
import com.prm.productsale.entity.UserFirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserFirebaseTokenRepo extends JpaRepository<UserFirebaseToken, Long> {
    Optional<UserFirebaseToken> findByUserId(Long userId);
}