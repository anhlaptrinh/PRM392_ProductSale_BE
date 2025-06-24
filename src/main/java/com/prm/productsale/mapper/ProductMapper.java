package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.CategoryResponse;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(source = "category.id", target = "categoryID")
  @Mapping(source = "category.categoryName", target = "categoryName")
  ProductResponse toProductResponse(ProductEntity productEntity);

  List<ProductResponse> toListProductResponse(List<ProductEntity> productEntityList);

  @Mapping(target = "productID", ignore = true)
  @Mapping(target = "category", ignore = true)
  ProductEntity toProductEntity(ProductRequest request);

  @Mapping(target = "productID", ignore = true)
  @Mapping(target = "category", ignore = true)
  void updateProductFromRequest(ProductRequest request, @MappingTarget ProductEntity entity);


  CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

}
