package com.prm.productsale.controller;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.ProductImp;
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
@RequestMapping(value = "/api/products")
@CrossOrigin
@Tag(name = "Product API", description = "API for Products")
public class ProductController {
    @Autowired
    ProductImp productImp;



    @Operation(
            summary = "Get all products",
            description = "ADMIN can get all products",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched all products successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    )
            }
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProducts() {
      BaseResponse response =
              new BaseResponse(200, "success", productImp.getAllProducts());
      return ResponseEntity.ok(response);
    }

  @Operation(
          summary = "Get a product by id",
          description = "ADMIN can get a product by ID",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Product not found"
                  )
          }
  )
  @GetMapping("/{productID}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getProductByID(@PathVariable int productID){
    BaseResponse response =
            new BaseResponse(200, "success", productImp.getProductByID(productID));
    return ResponseEntity.ok(response);
  }

  @Operation(
          summary = "Create a new product",
          description = "ADMIN can create a new product",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "success",
                          content = @Content(
                                  mediaType = "application/json",
                                  schema = @Schema(implementation = BaseResponse.class)
                          )
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Invalid input provided"
                  )
          }
  )
  @PostMapping()
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
    BaseResponse response =
            new BaseResponse(200, "success", productImp.createProduct(request));
    return ResponseEntity.ok(response);
  }

    @Operation(
            summary = "Update an existing product",
            description = "ADMIN can update a product by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    @PutMapping("/{productID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable int productID,@RequestBody ProductRequest request) {
      BaseResponse response =
              new BaseResponse(200, "success", productImp.updateProduct(productID, request));
        return ResponseEntity.ok(response);
    }
    @Operation(
            summary = "Delete a product",
            description = "ADMIN can delete a product by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    @DeleteMapping("/{productID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable int productID) {
      BaseResponse response =
              new BaseResponse(200, "success");
      productImp.deleteProduct(productID);
      return ResponseEntity.ok(response);
    }

















}
