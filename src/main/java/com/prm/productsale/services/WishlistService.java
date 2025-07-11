package com.prm.productsale.services;



import com.prm.productsale.dto.response.WishListResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WishlistService {
    void addToWishlist(int productId);
    List<WishListResponse> findAll();
    void deleteWishList();
    Page<WishListResponse> findByUserId(int page, int size);

}
