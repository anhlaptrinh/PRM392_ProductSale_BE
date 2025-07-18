package com.prm.productsale.repository;

import com.prm.productsale.entity.WishlistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface WishlistRepo extends JpaRepository<WishlistEntity,Integer> {
    Boolean existsByProductId(int productId);
    Page<WishlistEntity> findByUserId(int userId, Pageable pageable);
    boolean existsByUserIdAndProductId(int userId, int productId);
    Optional<WishlistEntity> findByUserIdAndProductId(int userId, int productId);

}
