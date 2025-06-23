package com.prm.productsale.services;

import com.prm.productsale.entity.StoreLocationEntity;
import com.prm.productsale.repository.StoreLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreLocationServicesImp implements StoreLocationServices {
  @Autowired
  private StoreLocationRepo storeLocationRepo;

  // =========================
  // 1. Các method "Read" / "List" (GET)
  // =========================

  @Override
  public List<StoreLocationEntity> getAllStoreLocations() {
    return storeLocationRepo.findAll();
  }

}
