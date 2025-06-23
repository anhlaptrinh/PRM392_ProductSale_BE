package com.prm.productsale.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
  private int code = 200;
  private String message;
  private Object data;

  public static BaseResponse getResponse(String message,Object data){
    BaseResponse response = new BaseResponse();
    response.setMessage(message);
    response.setData(data);
    return response;
  }

  public BaseResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
