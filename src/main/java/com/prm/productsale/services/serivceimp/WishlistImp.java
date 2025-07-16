package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.WishListResponse;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.entity.WishlistEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.WishlistMapper;
import com.prm.productsale.repository.ProductRepo;
import com.prm.productsale.repository.WishlistRepo;
import com.prm.productsale.services.LoginServices;
import com.prm.productsale.services.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class WishlistImp implements WishlistService {
    @Autowired
    private WishlistRepo wishlistRepo;
    @Autowired
    private LoginServices loginServices;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartImp cartImp;
    @Override

//    public void addToWishlist(int productId) {
//        WishlistEntity wishlistEntity = new WishlistEntity();
//        UserEntity user = loginServices.getUser();
//        ProductEntity product = productRepo.findById(productId)
//                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
//        if(wishlistRepo.existsByProductId(productId)) throw new AppException(ErrorCode.DUPLICATE_WISHLIST);
//        wishlistEntity.setUser(user);
//
//        wishlistEntity.setProduct(product);
//        wishlistRepo.save(wishlistEntity);
//
//
//    }

    public void addToWishlist(int productId) {
        UserEntity user = loginServices.getUser();
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));

        if(wishlistRepo.existsByUserIdAndProductId(user.getId(), productId)) {
            throw new AppException(ErrorCode.DUPLICATE_WISHLIST);
        }

        WishlistEntity wishlistEntity = new WishlistEntity();
        wishlistEntity.setUser(user);
        wishlistEntity.setProduct(product);
        wishlistRepo.save(wishlistEntity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<WishListResponse> findAll() {
        List<WishlistEntity> wishlistEntities = wishlistRepo.findAll();
        return WishlistMapper.INSTANCE.toListWishListResponse(wishlistEntities);
    }

    @Override
    public void deleteWishList(int id,Boolean isCreate) {
        WishlistEntity wishlistEntity =
                wishlistRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.WISHLIST_NOT_FOUND));
        if(isCreate == true){

            CartItemRequest cartItemRequest = new CartItemRequest(wishlistEntity.getProduct().getId(),1);
            cartImp.createItem(cartItemRequest);
            wishlistRepo.delete(wishlistEntity);
        }
        else{
            wishlistRepo.delete(wishlistEntity);
        }
    }

    @Override
    public Page<WishListResponse> findByUserId(int page,int size) {
        UserEntity user = loginServices.getUser();
        Pageable pageable =  PageRequest.of(page, size, Sort.by("id").descending());
        Page<WishlistEntity> wishlist = wishlistRepo.findByUserId(user.getId(),pageable);
        return wishlist.map(WishlistMapper.INSTANCE::toWishListResponse);
    }
}
