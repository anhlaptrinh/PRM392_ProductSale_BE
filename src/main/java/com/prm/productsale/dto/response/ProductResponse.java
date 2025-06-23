package com.prm.productsale.dto.response;

import com.prm.productsale.entity.CategoryEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
  int id;
  String productName;
  String description;
  BigDecimal price;
  CategoryEntity category;
}
