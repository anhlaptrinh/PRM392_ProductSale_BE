package com.prm.productsale.services;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.ProductResponse;
import java.math.BigDecimal;
import java.util.List;

public interface ProductServices {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductByID(int productID);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(int productID, ProductRequest request);
    void deleteProduct(int id);
    List<ProductResponse> filterProducts(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort);
}
