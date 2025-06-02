package com.prm.productsale.controller;

import com.prm.productsale.dto.request.ProductRequest;
import com.prm.productsale.response.BaseResponse;
import com.prm.productsale.services.serivceimp.ProductImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
@CrossOrigin
@Tag(name = "Product API", description = "API for Products")
public class ProductController {
    @Autowired
    ProductImp productImp;

  @Operation(
          summary = "Create a new product",
          description = "MEMBER can create a new product",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Created product successfully",
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
  public ResponseEntity<?> createProduct() {
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Created advice successfully");
    return ResponseEntity.ok(response);
  }

    @Operation(
            summary = "Get all products",
            description = "MEMBER can view the product list",
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
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
       BaseResponse response=getResponse("Success",productImp.getAll());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing product",
            description = "MEMBER can update a product by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated product successfully",
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
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request) {
        productImp.updateProduct(request);
        return ResponseEntity.ok(getResponse("Update Success",""));
    }
    @Operation(
            summary = "Delete a product",
            description = "MEMBER can delete a product by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleted product successfully",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productImp.deleteProduct(id);
        return ResponseEntity.ok(getResponse("Delete Success",""));
    }













    public static BaseResponse getResponse(String message,Object data){
      BaseResponse response = new BaseResponse();
      response.setMessage(message);
      response.setData(data);
      return response;
    }

}
