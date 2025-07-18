package com.prm.productsale.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    private int userId;
    private BigDecimal totalAmount;
    private String paymentMethod; // COD hoặc MOMO
}