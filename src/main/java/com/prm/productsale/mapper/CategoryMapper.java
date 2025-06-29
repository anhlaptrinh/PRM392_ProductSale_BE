package com.prm.productsale.mapper;

import com.prm.productsale.dto.request.CategoryRequest;
import com.prm.productsale.dto.response.CategoryResponse;
import com.prm.productsale.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CategoryMapper {

  // =========================
  // 1. Entity -> Response Mapping
  // =========================

  @Mapping(source = "products", target = "productList")
  CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

  List<CategoryResponse> toListCategoryResponse(List<CategoryEntity> categoryEntityList);

  // =========================
  // 2. Request -> Entity Mapping
  // =========================

  @Mapping(target = "categoryID", ignore = true)
  @Mapping(target = "products", ignore = true)
  CategoryEntity toCategoryEntity(CategoryRequest categoryRequest);

  @Mapping(target = "categoryID", ignore = true)
  @Mapping(target = "products", ignore = true)
  void updateCategoryFromRequest(CategoryRequest request, @MappingTarget CategoryEntity categoryEntity);
}
