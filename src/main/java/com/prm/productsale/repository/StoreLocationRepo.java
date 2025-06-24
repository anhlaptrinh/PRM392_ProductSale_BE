package com.prm.productsale.repository;

import com.prm.productsale.entity.StoreLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLocationRepo extends JpaRepository<StoreLocationEntity, Integer> {
}
