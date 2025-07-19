package com.prm.productsale.validator;

import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

  @Autowired
  private ProductRepo productRepo;

  public ProductEntity validateExist(int productId) {
    return productRepo.findById(productId)
            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
  }
}
