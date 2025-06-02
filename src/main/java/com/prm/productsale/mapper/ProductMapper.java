package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.CategoryResponse;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "category", target = "category")
    @Mapping(source = "fullDesc", target = "description")
    ProductResponse toProductResponse(ProductEntity productEntity);
    List<ProductResponse> toListProductResponse(List<ProductEntity> productEntityList);
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);
}
