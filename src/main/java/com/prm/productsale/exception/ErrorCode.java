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
  UNCATEGORIZED_EXCEPTION(1001, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
  UNAUTHORIZED_EXCEPTION(999, "do not permission", HttpStatus.FORBIDDEN),

  USER_NOT_EXIST(403, "user not existed", HttpStatus.BAD_REQUEST);


  private int code;
  private String message;
  private HttpStatusCode httpStatusCode;
}
