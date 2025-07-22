package com.prm.productsale.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
  int categoryID;
  String categoryName;
  String imageURL;
}
