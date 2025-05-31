package com.prm.productsale.services;

import com.prm.productsale.dto.request.UserRequest;

public interface UserServices {
  void register(UserRequest request, String verificationCode);
}
