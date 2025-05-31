package com.prm.productsale.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
  private int code = 200;
  private String message;
  private Object data;
}
