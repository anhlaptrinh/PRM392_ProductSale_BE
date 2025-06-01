package com.prm.productsale.services;

import com.prm.productsale.dto.DataMailDTO;
import com.prm.productsale.dto.response.UserResponse;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.UserMapper;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.dto.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.prm.productsale.utils.DataUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServicesImp implements UserServices {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  MailServices mailServices;
  @Autowired
  MailServicesImp mailServicesImp;

  // =========================
  // 1. Các method "Read" / "List" (GET)
  // =========================

  @Override
  @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
  public UserResponse getMyInfo() {
    var context = SecurityContextHolder.getContext();
    String email = context.getAuthentication().getName();

    UserEntity user = userRepo.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
    return UserMapper.INSTANCE.toUserResponse(user);
  }

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

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    UserResponse userResponse = getMyInfo();

    Optional<UserEntity> user = userRepo.findByEmail(userResponse.getEmail());
    UserEntity userEntity = user.get();
    if(passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
      userEntity.setPassword(passwordEncoder.encode(newPassword));
      userRepo.save(userEntity);
    }
    else throw new AppException(ErrorCode.PASSWORD_NOT_CORRECT);
  }

  // =========================
  // 4. Các method "Delete" (DELETE) hoặc đặc biệt
  // =========================

  @Override
  public Boolean forgotPassword(String email) {
    UserEntity user = userRepo.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
    String rawPassword = DataUtils.generateAndHashPassword(8); // Tạo mật khẩu 8 ký tự

    // 2. Băm mật khẩu trước khi lưu vào DB
    String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));
    user.setPassword(hashedPassword);
    userRepo.save(user);
    try{
      DataMailDTO dataMailDTO = new DataMailDTO();
      dataMailDTO.setTo(user.getEmail());
      dataMailDTO.setSubject("Forgot password");
      Map<String,Object> props = new HashMap<>();
      props.put("username", user.getUsername());
      props.put("email", user.getEmail());
      props.put("password", rawPassword);
      dataMailDTO.setProps(props);
      mailServices.sendHtmlMail(dataMailDTO,"client");
      return true;

    }catch (Exception e){
      e.printStackTrace();
    }
    return false;
  }

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
