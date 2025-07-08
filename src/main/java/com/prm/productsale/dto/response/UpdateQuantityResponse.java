package com.prm.productsale.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateQuantityResponse {
    private int itemId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal cartTotal;
}
