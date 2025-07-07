package com.prm.productsale.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductOfCartItem {
     int id;
     String name;
     String imagePath;
}
