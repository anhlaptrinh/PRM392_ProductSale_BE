package com.prm.productsale.services;

import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImp implements UserServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  MailServicesImp mailServicesImp;

  // =========================
  // 1. Các method "Read" / "List" (GET)
  // =========================



  // =========================
  // 2. Các method "Create" (POST)
  // =========================

  @Override
  public void register(UserRequest request, String verificationCode) {
    boolean result = mailServicesImp.verifyCode(request.getEmail(), verificationCode);

    if (!result) {
      throw new AppException(ErrorCode.VERIFICATION_CODE_ERROR);
    }

    Optional<UserEntity> userEntity = userRepo.findByEmail(request.getEmail());
    if(userEntity.isPresent()) throw new AppException(ErrorCode.USER_EXIST);

    UserEntity user = new UserEntity();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
    user.setUsername(request.getUsername());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setAddress(request.getAddress());
    setDefaultRole(user, "MEMBER");
    userRepo.save(user);
  }

  // =========================
  // 3. Các method "Update" (PUT/PATCH)
  // =========================



  // =========================
  // 4. Các method "Delete" (DELETE) hoặc đặc biệt
  // =========================



  // =========================
  // 5. Các method "Utility" (private helpers)
  // =========================

  private void setDefaultRole(UserEntity user, String roleName) {
    if (user.getRole() == null) {
      user.setRole(roleName);
    }
  }

  private void setUpRole(UserEntity user, String roleName) {
    user.setRole(roleName);
  }
}
