package com.prm.productsale.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
  private int code = 200;
  private String message;
  private Object data;

  public BaseResponse() {
  }

  public BaseResponse(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public BaseResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
