package com.prm.productsale.controller;

import com.prm.productsale.dto.request.UserRequest;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.response.BaseResponse;
import com.prm.productsale.services.LoginServices;
import com.prm.productsale.services.MailServicesImp;
import com.prm.productsale.services.UserServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/authentication")
@CrossOrigin
@Tag(name = "Authenticated API", description = "API for user authentication")
public class LoginController {
  @Autowired
  private LoginServices loginServices;
  @Autowired
  private UserServicesImp userServicesImp;
  @Autowired
  private MailServicesImp mailServicesImp;


  // ============================
  // 1. Đăng ký (Register) & Gửi mã xác thực
  // ============================

  @Operation(
          summary = "Send Verification Code API",
          description = "Send Verification Code",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Sent verification code successfully",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PostMapping("/verification-code")
  public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
    BaseResponse response = new BaseResponse();
    try {
      mailServicesImp.sendVerificationEmail(email);
      response.setCode(200);
      response.setMessage("Sent verification code successfully");
      return ResponseEntity.ok(response);
    } catch (MessagingException e) {
      response.setCode(400);
      response.setMessage("Error sending email: " + e.getMessage());
      return ResponseEntity.ok(response);
    }
  }

  @Operation(
          summary = "User resister",
          description = "GUEST can resister",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Resister successful",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody UserRequest request, @RequestParam String verificationCode){
    userServicesImp.register(request, verificationCode);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Resister successful");
    return ResponseEntity.ok(response);
  }


  // ============================
  // 2. Đăng nhập (Login)
  // ============================

  @Operation(
          summary = "User Login",
          description = "Authenticate user with email and password to receive a JWT token",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "login successful",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Login successful\",\n  \"data\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"\n}"
                                  )
                          )
                  ),
          }
  )
  @PostMapping("/login")
  public ResponseEntity<?> login(
          @Parameter(description = "User email", required = true, example = "mem@gmail.com")
          @RequestParam /*@Valid*/ String email,
          @Parameter(description = "User password", required = true, example = "1234")
          @RequestParam String password){

    String token = loginServices.login(email, password);

    BaseResponse response = new BaseResponse();
    if(Objects.equals(token, "")) throw new AppException(ErrorCode.USER_NOT_EXIST);
    response.setData(token);
    response.setMessage("Login successful");
    response.setCode(200);

    return ResponseEntity.ok(response);
  }


  // ============================
  // 3. Quên mật khẩu (Forgot Password)
  // ============================

  @Operation(
          summary = "User Forgot Password",
          description = "Reset Password",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Reset password successful",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class),
                                  examples = @ExampleObject(
                                          name = "Success Response",
                                          value = "{\n  \"code\": 200,\n  \"message\": \"Reset password successful\",\n  \"data\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"\n}"
                                  )
                          )
                  ),
          }
  )
  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestParam String email){
    userServicesImp.forgotPassword(email);
    BaseResponse response = new BaseResponse();
    response.setMessage("Your Password is reset Check your mail");
    return ResponseEntity.ok(response);
  }


  // ============================
  // 4. Đổi mật khẩu (Change Password)
  // ============================

  @Operation(
          summary = "User change Password",
          description = "Password Change",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Changed successful",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PutMapping("/password-change")
  @PreAuthorize("hasRole('MEMBER')")
  public ResponseEntity<?> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
    userServicesImp.changePassword(oldPassword, newPassword);
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Changed password successful");
    return ResponseEntity.ok(response);
  }
}
