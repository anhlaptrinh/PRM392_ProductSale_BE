package com.prm.productsale.controller;

import com.prm.productsale.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
@CrossOrigin
@Tag(name = "Product API", description = "API for Products")
public class ProductController {

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
}
