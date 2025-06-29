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
  VERIFICATION_CODE_ERROR(400, "Verification code is incorrect", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED_EXCEPTION(999, "do not permission", HttpStatus.FORBIDDEN),
  UNCATEGORIZED_EXCEPTION(1000, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),

  // ============================
  // 1. User-related (400 → 403)
  // ============================
  USER_EXIST(400, "user existed", HttpStatus.BAD_REQUEST),
  PASSWORD_NOT_CORRECT(401, "password is not correct", HttpStatus.BAD_REQUEST),
  USER_NOT_EXIST(402, "user not existed", HttpStatus.BAD_REQUEST),

  // ============================
  // 2. Product-related (400 → 403)
  // ============================
  PRODUCT_NOT_EXIST_EXCEPTION(400, "product not exist", HttpStatus.BAD_REQUEST),
  PRODUCT_LIST_NOT_EXIST_EXCEPTION(401, "product list not exist", HttpStatus.BAD_REQUEST),

  // ============================
  // 3. Category-related (400 → 403)
  // ============================
  CATEGORY_NOT_EXIST_EXCEPTION(400, "category not exist", HttpStatus.BAD_REQUEST),
  CATEGORY_EXIST_EXCEPTION(401, "category exist", HttpStatus.BAD_REQUEST),
  CATEGORY_HAS_PRODUCTS_EXCEPTION(402, "category has products", HttpStatus.BAD_REQUEST),

  // ============================
  // 4. Order-related (400 → 403)
  // ============================
  ORDER_NOT_EXIST(404,"Cannot found Your Order" ,HttpStatus.NOT_FOUND ),
  INVALID_FORMAT(403,"Invalid format STRING" ,HttpStatus.BAD_REQUEST);




  private int code;
  private String message;
  private HttpStatusCode httpStatusCode;
}
