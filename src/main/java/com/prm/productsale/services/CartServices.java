package com.prm.productsale.services;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.CartItemResponse;
import com.prm.productsale.dto.response.UpdateQuantityResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CartServices {
    void createItem(CartItemRequest request);
    UpdateQuantityResponse updateItem(int cartItemId, int quantity);
    void deleteItem(int itemId);
    List<CartItemResponse> getListItem();
    BigDecimal getTotalAmount();
    void deleteAll();
}
