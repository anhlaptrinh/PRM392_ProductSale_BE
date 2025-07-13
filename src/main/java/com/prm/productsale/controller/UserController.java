package com.prm.productsale.controller;

import com.prm.productsale.dto.request.UserRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.UserServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin
@Tag(name = "User API", description = "API for ADMIN to manage")
public class UserController {
  @Autowired
  UserServicesImp userServicesImp;

  // ============================
  // 1. Lấy toàn bộ danh sách user
  // ============================

  @Operation(
          summary = "Get all users API",
          description = "ADMIN can get all users",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllUsers() {
    BaseResponse response =
            new BaseResponse(200, "success", userServicesImp.getAllUsers());
    return ResponseEntity.ok(response);
  }

  // ============================
  // 2. Lấy user bằng ID
  // ============================

  @Operation(
          summary = "Get user by Id API",
          description = "ADMIN can get user by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @GetMapping("/{userID}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getUserByID(@PathVariable int userID) {
    BaseResponse response =
            new BaseResponse(200, "success", userServicesImp.getUserByID(userID));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Get current user API",
          description = "MEMBER can their info",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @GetMapping("/user-info")
  @PreAuthorize("hasRole('MEMBER')")
  public ResponseEntity<?> getCurrentUser() {
    BaseResponse response =
            new BaseResponse(200, "success", userServicesImp.getMyInfo());
    return ResponseEntity.ok(response);
  }

  // ============================
  // 2. Cấp nhật user
  // ============================

  @Operation(
          summary = "Update user API",
          description = "MEMBER can update their info",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PutMapping()
  @PreAuthorize("hasRole('MEMBER')")
  public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
    BaseResponse response =
            new BaseResponse(200, "success", userServicesImp.updateUser(userRequest));
    return ResponseEntity.ok(response);
  }
}