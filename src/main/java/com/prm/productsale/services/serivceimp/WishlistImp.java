package com.prm.productsale.services.serivceimp;

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
    @Override
    public void addToWishlist(int productId) {
        WishlistEntity wishlistEntity = new WishlistEntity();
        UserEntity user = loginServices.getUser();
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_EXCEPTION));
        if(wishlistRepo.existsByProductId(productId)) throw new AppException(ErrorCode.DUPLICATE_WISHLIST);
        wishlistEntity.setUser(user);

        wishlistEntity.setProduct(product);
        wishlistRepo.save(wishlistEntity);


    }

    @Override
    public List<WishListResponse> findAll() {
        List<WishlistEntity> wishlistEntities = wishlistRepo.findAll();
        return WishlistMapper.INSTANCE.toListWishListResponse(wishlistEntities);
    }

    @Override
    public void deleteWishList() {

    }
}
