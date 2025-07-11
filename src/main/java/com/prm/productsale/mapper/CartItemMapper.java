package com.prm.productsale.mapper;

import com.prm.productsale.dto.ProductOfCartItem;
import com.prm.productsale.dto.response.CartItemResponse;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.entity.ProductEntity;
import com.prm.productsale.utils.CommonMapUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends CommonMapUtils {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItemResponse toCartItemResponse(CartItemEntity entity);
    List<CartItemResponse> toListCartItemResponse(List<CartItemEntity> cartItemResponseList);
    @Mapping(source = "productName", target = "name")
    @Mapping(source = "imageURL", target = "imagePath",qualifiedByName = "nullToEmpty")
    @Mapping(source = "price", target = "price")

    ProductOfCartItem toProductOfCartItem(ProductEntity product);


}
