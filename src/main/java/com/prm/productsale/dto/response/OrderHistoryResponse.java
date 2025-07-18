package com.prm.productsale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {
    private int id;
    private String date;
    private BigDecimal total;
    private boolean shipped;
    private boolean arrived;
}