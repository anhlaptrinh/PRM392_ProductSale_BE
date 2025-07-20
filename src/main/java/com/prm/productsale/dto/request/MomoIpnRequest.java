package com.prm.productsale.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MomoIpnRequest {
    private String partnerCode;
    private String accessKey;
    private String orderId;
    private String requestId;
    private String amount;
    private String orderInfo;
    private String orderType;
    private Long transId;
    private int resultCode;
    private String message;
    private String payType;
    private Long responseTime;
    private String extraData;
    private String signature;
}
