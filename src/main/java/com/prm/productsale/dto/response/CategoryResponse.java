package com.prm.productsale.dto.response;

import com.prm.productsale.entity.ProductEntity;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private int id;
    private String categoryName;

}
