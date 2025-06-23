package com.prm.productsale.mapper;

import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
  List<ProductResponse> toListProductResponse(List<ProductEntity> productEntityList);
}
