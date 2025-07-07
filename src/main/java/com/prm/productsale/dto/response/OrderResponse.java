package com.prm.productsale.dto.response;

import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    int id;
    UserEntity user;
    String pmMethod;
    String bill;
    String orderStatus;
    LocalDateTime orderDate;

}
