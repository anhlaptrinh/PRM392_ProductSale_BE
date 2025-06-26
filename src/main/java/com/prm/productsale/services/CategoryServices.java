package com.prm.productsale.services;

import com.prm.productsale.dto.request.CategoryRequest;
import com.prm.productsale.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryServices {
  List<CategoryResponse> getAllCategories();
  CategoryResponse getCategoryByID(int categoryID);
  CategoryResponse createCategory(CategoryRequest request);
  CategoryResponse updateCategory(int categoryID, CategoryRequest request);
  void deleteCategoryByID(int categoryID);
}
