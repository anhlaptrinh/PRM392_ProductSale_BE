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
  INVALID_SORT_TYPE(400, "sort type is invalid", HttpStatus.BAD_REQUEST),
  INVALID_PRICE_RANGE(400, "price must be greater than or equal to 0", HttpStatus.BAD_REQUEST),
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
  INVALID_FORMAT(403,"Invalid format STRING" ,HttpStatus.BAD_REQUEST),
  CART_ITEM_NOT_FOUND(403,"Not found your item cart" ,HttpStatus.BAD_REQUEST),
  DUPLICATE_WISHLIST(403,"Duplicate item wishlist" ,HttpStatus.BAD_REQUEST ),

  WISHLIST_NOT_FOUND(403,"Wishlist not found" ,HttpStatus.BAD_REQUEST ),


  // ============================
  // 5. Notification-related (400 → 403)
  // ============================
  NOTIFICATION_NOT_FOUND(400, "notification not found", HttpStatus.BAD_REQUEST),

  // ============================
  // 5. Review-related (400 → ...)
  // ============================
  RATING_OUT_OF_RANGE(400, "RATING must be between 1 and 5", HttpStatus.BAD_REQUEST),
  FORBIDDEN(403, "You are not allowed to delete this review", HttpStatus.FORBIDDEN),
  REVIEW_NOT_FOUND(404, "Review not found", HttpStatus.NOT_FOUND),
  REVIEW_ALREADY_EXISTS(409, "You already reviewed this product", HttpStatus.BAD_REQUEST),
  REVIEW_ALREADY_DELETED(410, "This review has already been deleted", HttpStatus.GONE),

  // ============================
  // 6. ReviewVote-related (400 → ...)
  // ============================
  INVALID_VOTE_TYPE(400, "Vote type must be 'up' or 'down'", HttpStatus.BAD_REQUEST),
  REVIEW_ALREADY_VOTED(409, "You have already voted this review", HttpStatus.CONFLICT);


  private int code;
  private String message;
  private HttpStatusCode httpStatusCode;
}
