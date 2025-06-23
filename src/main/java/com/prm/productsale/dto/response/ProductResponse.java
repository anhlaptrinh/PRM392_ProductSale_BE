package com.prm.productsale.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
  int productID;
  String productName;
  String briDesc;
  String fullDesc;
  String technic;
  BigDecimal price;
  String imageURL;
  int categoryID;
  String categoryName;
}
