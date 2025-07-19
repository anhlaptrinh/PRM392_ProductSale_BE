package com.prm.productsale.validator;

import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

  @Autowired
  private UserRepo userRepo;

  public UserEntity validateExist(int userId) {
    return userRepo.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
  }
}

