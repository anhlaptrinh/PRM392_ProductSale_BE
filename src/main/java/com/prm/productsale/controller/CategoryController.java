package com.prm.productsale.controller;

import com.prm.productsale.dto.request.CategoryRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.CategoryImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/categories")
@CrossOrigin
@Tag(name = "Category API", description = "API for Category")
public class CategoryController {
  @Autowired
  private CategoryImp categoryImp;

  // ============================
  // 1. Lấy toàn bộ danh sách categories
  // ============================

  @Operation(
          summary = "Get all categories API",
          description = "ADMIN can get all categories",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @GetMapping
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllCategories() {
    BaseResponse response =
            new BaseResponse(200, "success", categoryImp.getAllCategories());
    return ResponseEntity.ok(response);
  }

  // ============================
  // 2. Lấy category bằng ID
  // ============================

  @Operation(
          summary = "Get category by ID API",
          description = "ADMIN can get category by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @GetMapping("/{categoryID}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getCategoryByID(@PathVariable int categoryID) {
    BaseResponse response =
            new BaseResponse(200, "success", categoryImp.getCategoryByID(categoryID));
    return ResponseEntity.ok(response);
  }

  // ============================
  // 3. Tạo category
  // ============================

  @Operation(
          summary = "Create category",
          description = "ADMIN can create category",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PostMapping
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", categoryImp.createCategory(request));
    return ResponseEntity.ok(response);
  }

  // ============================
  // 4. Cập nhật category
  // ============================

  @Operation(
          summary = "Update an existing category",
          description = "ADMIN can update category by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @PutMapping("/{categoryID}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateCategory(@PathVariable int categoryID, @RequestBody CategoryRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", categoryImp.updateCategory(categoryID, request));
    return ResponseEntity.ok(response);
  }

  // ============================
  // 5. Xóa category
  // ============================

  @Operation(
          summary = "Delete an existing category",
          description = "ADMIN can delete category by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
          }
  )
  @DeleteMapping("/{categoryID}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteCategoryByID(@PathVariable int categoryID) {
    BaseResponse response =
            new BaseResponse(200, "success");
    categoryImp.deleteCategoryByID(categoryID);
    return ResponseEntity.ok(response);
  }
}
