package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.WishListResponse;
import com.prm.productsale.entity.WishlistEntity;
import com.prm.productsale.utils.CommonMapUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper extends CommonMapUtils {
    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.imageURL", target = "imageURL",qualifiedByName = "nullToEmpty")
    @Mapping(source = "product.briDesc",target = "briDesc")
    @Mapping(source = "product.price",target = "price")
    WishListResponse toWishListResponse(WishlistEntity entity);
    List<WishListResponse> toListWishListResponse(List<WishlistEntity> listResponses);
}
