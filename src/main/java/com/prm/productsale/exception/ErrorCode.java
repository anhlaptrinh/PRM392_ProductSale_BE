package com.prm.productsale.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
  // --- Generic / Common Exceptions ---
  VERIFICATION_CODE_ERROR(404, "Verification code is incorrect", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED_EXCEPTION(999, "do not permission", HttpStatus.FORBIDDEN),
  UNCATEGORIZED_EXCEPTION(1001, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),

  // ============================
  // 1. User-related (400 → 403)
  // ============================
  USER_EXIST(400, "user existed", HttpStatus.BAD_REQUEST),
  PASSWORD_NOT_CORRECT(400, "password is not correct", HttpStatus.BAD_REQUEST),
  USER_NOT_EXIST(403, "user not existed", HttpStatus.BAD_REQUEST),
  PRODUCT_NOT_EXIST(404,"product is empty" ,HttpStatus.NOT_FOUND);


  private int code;
  private String message;
  private HttpStatusCode httpStatusCode;
}
