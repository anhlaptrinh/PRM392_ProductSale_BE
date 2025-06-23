package com.prm.productsale.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
  String productName;
  String briDesc;
  String fullDesc;
  String technic;
  BigDecimal price;
  String imageURL;
  int categoryID;
}
