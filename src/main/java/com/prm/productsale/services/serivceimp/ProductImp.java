package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ProductMapper;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if(productEntityList.isEmpty()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        return ProductMapper.INSTANCE.toListProductResponse(productEntityList);
    }

    @Override
    public void deleteProduct(int id) {
        Optional<ProductEntity> product = productRepo.findById(id);
        if(product.isEmpty()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        productRepo.deleteById(id);
    }

    @Override
    public void updateProduct(ProductRequest productRequest) {

    }

    @Override
    public ProductRequest saveProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse getProductByID(int id) {
        Optional<ProductEntity> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()){
            ProductEntity product = optionalProduct.get();
            return ProductMapper.INSTANCE.toProductResponse(product);
        } else {
            throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        }
    }
}
