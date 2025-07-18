package com.prm.productsale.services;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.CartItemResponse;
import com.prm.productsale.dto.response.UpdateQuantityResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface CartServices {
    void createItem(CartItemRequest request);
    UpdateQuantityResponse updateItem(int cartItemId, int quantity);
    void deleteItem(int itemId);
    Page<CartItemResponse> getListItem(int page,int size);
    BigDecimal getTotalAmount();
    void deleteAll();
    int getItemCount();
    int getItemCountByUserId(Long userId);

}
