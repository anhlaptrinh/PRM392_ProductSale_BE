package com.prm.productsale.dto.response;

import com.prm.productsale.dto.ProductOfCartItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    private int id;
    private int quantity;
    private BigDecimal price ;
    private ProductOfCartItem product;


}
