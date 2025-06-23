package com.prm.productsale.repository;

import com.prm.productsale.entity.StoreLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreLocationRepo extends JpaRepository<StoreLocationEntity, Integer> {
}
