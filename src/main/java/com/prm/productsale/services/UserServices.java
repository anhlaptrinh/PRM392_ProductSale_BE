package com.prm.productsale.services;

import com.prm.productsale.dto.request.UserRequest;
import com.prm.productsale.dto.response.UserResponse;

import java.util.List;

public interface UserServices {
  UserResponse getMyInfo();
  List<UserResponse> getAllUsers();
  UserResponse getUserByID(int userID);
  void register(UserRequest request, String verificationCode);
  void changePassword(String oldPassword, String newPassword);
  UserResponse updateUser(UserRequest userRequest);
  Boolean forgotPassword(String email);
}
