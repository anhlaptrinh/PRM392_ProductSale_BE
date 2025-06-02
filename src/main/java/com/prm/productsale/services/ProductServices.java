package com.prm.productsale.services;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.ProductResponse;

import java.util.List;

public interface ProductServices {
    List<ProductResponse> getAll();
    void deleteProduct(int id);
    void updateProduct(ProductRequest productRequest);
    ProductRequest saveProduct(ProductRequest productRequest);
}
