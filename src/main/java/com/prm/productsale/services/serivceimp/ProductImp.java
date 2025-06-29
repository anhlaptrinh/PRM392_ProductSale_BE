package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.ProductMapper;
import com.prm.productsale.repository.CategoryRepo;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImp implements ProductServices {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductMapper productMapper;

    // =========================
    // 1. Các method "Read" / "List" (GET)
    // =========================

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductEntity> productEntityList = productRepo.findAll();
        if(productEntityList.isEmpty()) throw new AppException(ErrorCode.PRODUCT_LIST_NOT_EXIST_EXCEPTION);
        return productMapper.toListProductResponse(productEntityList);
    }

    @Override
    public ProductResponse getProductByID(int productID) {
        ProductEntity entity = productRepo.findById(productID)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
        return productMapper.toProductResponse(entity);
    }

    // =========================
    // 2. Các method "Create" (POST)
    // =========================

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        ProductEntity productEntity = productMapper.toProductEntity(request);

        CategoryEntity categoryEntity = categoryRepo.findById(request.getCategoryID())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

        productEntity.setCategory(categoryEntity);

        ProductEntity saved = productRepo.save(productEntity);
        return productMapper.toProductResponse(saved);
    }

    // =========================
    // 3. Các method "Update" (PUT/PATCH)
    // =========================

    public ProductResponse updateProduct(int productID, ProductRequest request) {
        ProductEntity existing = productRepo.findById(productID)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));

        productMapper.updateProductFromRequest(request, existing);

        CategoryEntity category = categoryRepo.findById(request.getCategoryID())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

        existing.setCategory(category);

        ProductEntity updated = productRepo.save(existing);

        return productMapper.toProductResponse(updated);
    }

    // =========================
    // 4. Các method "Delete" (DELETE) hoặc đặc biệt
    // =========================

    @Override
    public void deleteProduct(int productId) {
        Optional<ProductEntity> product = productRepo.findById(productId);
        if(product.isEmpty()) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION);
        productRepo.deleteById(productId);
    }

    // =========================
    // 5. Các method "Utility" (private helpers)
    // =========================
}

