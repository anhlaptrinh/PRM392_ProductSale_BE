package com.prm.productsale.services.serivceimp;

import com.prm.productsale.dto.request.CategoryRequest;
import com.prm.productsale.dto.response.CategoryResponse;
import com.prm.productsale.entity.CategoryEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.mapper.CategoryMapper;
import com.prm.productsale.repository.CategoryRepo;
import com.prm.productsale.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImp implements CategoryServices {
  @Autowired
  private CategoryRepo categoryRepo;
  @Autowired
  private CategoryMapper categoryMapper;

  // =========================
  // 1. Các method "Read" / "List" (GET)
  // =========================

  @Override
  public List<CategoryResponse> getAllCategories() {
    List<CategoryEntity> categoryEntityList = categoryRepo.findAll();
    return categoryMapper.toListCategoryResponse(categoryEntityList);
  }

  @Override
  public CategoryResponse getCategoryByID(int categoryID) {
    CategoryEntity categoryEntity = categoryRepo.findById(categoryID)
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));
    return categoryMapper.toCategoryResponse(categoryEntity);
  }

  // =========================
  // 2. Các method "Create" (POST)
  // =========================

  @Override
  public CategoryResponse createCategory(CategoryRequest request) {
    Optional<CategoryEntity> exsting = categoryRepo.findByCategoryName(request.getCategoryName());
    if(exsting.isPresent()) throw new AppException(ErrorCode.CATEGORY_EXIST_EXCEPTION);

    CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(request);

    CategoryEntity saved = categoryRepo.save(categoryEntity);
    if(saved.getImageURL() == null) saved.setImageURL("");

    return categoryMapper.toCategoryResponse(saved);
  }

  // =========================
  // 3. Các method "Update" (PUT/PATCH)
  // =========================

  @Override
  public CategoryResponse updateCategory(int categoryID, CategoryRequest request) {
    CategoryEntity existing = categoryRepo.findById(categoryID)
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

    categoryMapper.updateCategoryFromRequest(request, existing);

    CategoryEntity updated = categoryRepo.save(existing);

    return categoryMapper.toCategoryResponse(updated);
  }

  // =========================
  // 4. Các method "Delete" (DELETE) hoặc đặc biệt
  // =========================

  @Override
  public void deleteCategoryByID(int categoryID) {
    CategoryEntity categoryEntity = categoryRepo.findById(categoryID)
            .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_EXCEPTION));

    if(categoryEntity.getProducts() != null && !categoryEntity.getProducts().isEmpty()) {
      throw new AppException(ErrorCode.CATEGORY_HAS_PRODUCTS_EXCEPTION);
    }

    categoryRepo.delete(categoryEntity);
  }
}
