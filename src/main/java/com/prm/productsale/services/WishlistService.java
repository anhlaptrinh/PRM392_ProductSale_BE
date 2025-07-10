package com.prm.productsale.services;



import com.prm.productsale.dto.response.WishListResponse;

import java.util.List;

public interface WishlistService {
    void addToWishlist(int productId);
    List<WishListResponse> findAll();
    void deleteWishList();

}
