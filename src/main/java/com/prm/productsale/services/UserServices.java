package com.prm.productsale.services;

import com.prm.productsale.dto.request.UserRequest;

public interface UserServices {
  Boolean forgotPassword(String email);
  void register(UserRequest request, String verificationCode);
}
