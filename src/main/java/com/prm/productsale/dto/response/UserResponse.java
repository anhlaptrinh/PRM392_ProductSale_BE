package com.prm.productsale.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class UserResponse {
  int id;
  String username;
  String email;
  String phoneNumber;
  String address;
  String role;
}
