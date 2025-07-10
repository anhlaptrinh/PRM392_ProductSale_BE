package com.prm.productsale.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;



@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
    int productId;
    int quantity;

}
