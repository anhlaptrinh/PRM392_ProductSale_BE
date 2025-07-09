package com.prm.productsale.dto.response;

import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishListResponse {
    int id;
    int productId;

    String productName;
    String briDesc;
    String imageURL;
    BigDecimal price;

}
