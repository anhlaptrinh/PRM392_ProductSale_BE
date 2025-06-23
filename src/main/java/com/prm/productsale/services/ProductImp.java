package com.prm.productsale.services;

import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ProductMapper;
import com.prm.productsale.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImp implements ProductServices {
  @Autowired
  ProductRepo productRepo;

  @Override
  public List<ProductResponse> getAll() {
    List<ProductEntity> productEntityList = productRepo.findAll();
    if(productEntityList.isEmpty()) throw new AppException(ErrorCode.USER_EXIST);
    return ProductMapper.INSTANCE.toListProductResponse(productEntityList);
  }

  @Override
  public void deleteProduct(int id) {
    Optional<ProductEntity> product = productRepo.findById(id);
    if(product.isEmpty()) throw new AppException(ErrorCode.USER_EXIST);
    productRepo.deleteById(id);
  }
}
