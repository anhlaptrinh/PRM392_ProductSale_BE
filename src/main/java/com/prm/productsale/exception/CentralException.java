package com.prm.productsale.exception;

import com.prm.productsale.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class CentralException {
  @ExceptionHandler({Exception.class})
  public ResponseEntity<?> centralLog(Exception e){
    BaseResponse response = new BaseResponse();

    response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
    response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler({AppException.class})
  public ResponseEntity<?> centralLog(AppException e){
    BaseResponse response = new BaseResponse();
    ErrorCode errorCode= e.getErrorCode();
    response.setMessage(errorCode.getMessage());
    response.setCode(errorCode.getCode());
    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e){
    BaseResponse response = new BaseResponse();
    String enumkey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
    ErrorCode errorCode = ErrorCode.valueOf(enumkey);
    response.setMessage(errorCode.getMessage());
    response.setCode(errorCode.getCode());
    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<?> handleAccess(AccessDeniedException e){
    BaseResponse response = new BaseResponse();
    ErrorCode errorCode= ErrorCode.UNAUTHORIZED_EXCEPTION;
    response.setMessage(errorCode.getMessage());
    response.setCode(errorCode.getCode());
    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<?> handleAuthenticate(AuthenticationException e){
    BaseResponse response = new BaseResponse();
    ErrorCode errorCode= ErrorCode.UNAUTHORIZED_EXCEPTION;
    response.setMessage(errorCode.getMessage());
    response.setCode(errorCode.getCode());
    return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
  }
}
