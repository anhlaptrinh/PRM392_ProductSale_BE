package com.prm.productsale.services;

import com.prm.productsale.dto.request.UserRequest;
import com.prm.productsale.dto.response.UserResponse;

public interface UserServices {
  UserResponse getMyInfo();
  void register(UserRequest request, String verificationCode);
  void changePassword(String oldPassword, String newPassword);
  Boolean forgotPassword(String email);
}
