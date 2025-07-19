package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.CategoryResponse;
import com.prm.productsale.dto.response.ProductResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.entity.ProductEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  // =========================
  // 1. Entity -> Response Mapping
  // =========================
  @Mapping(source = "id", target = "productID")
  @Mapping(source = "category.categoryID", target = "categoryID")
  @Mapping(source = "category.categoryName", target = "categoryName", qualifiedByName = "nullToEmpty")
  @Mapping(source = "productName", target = "productName", qualifiedByName = "nullToEmpty")
  @Mapping(source = "briDesc", target = "briDesc", qualifiedByName = "nullToEmpty")
  @Mapping(source = "fullDesc", target = "fullDesc", qualifiedByName = "nullToEmpty")
  @Mapping(source = "technic", target = "technic", qualifiedByName = "nullToEmpty")
  @Mapping(source = "imageURL", target = "imageURL", qualifiedByName = "nullToEmpty")
  ProductResponse toProductResponse(ProductEntity productEntity);

  List<ProductResponse> toListProductResponse(List<ProductEntity> productEntityList);

  // =========================
  // 2. Request -> Entity Mapping
  // =========================

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", ignore = true)
  ProductEntity toProductEntity(ProductRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "category", ignore = true)
  void updateProductFromRequest(ProductRequest request, @MappingTarget ProductEntity entity);

  // =========================
  // 3. Utility (Handle null -> "")
  // =========================

  @Named("nullToEmpty")
  static String nullToEmpty(String value) {
    return value == null ? "" : value;
  }
}
