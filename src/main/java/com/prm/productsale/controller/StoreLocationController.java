package com.prm.productsale.controller;

import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.StoreLocationImp;
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
@RequestMapping(value = "/api/store-locations")
@CrossOrigin
@Tag(name = "Store Locations API", description = "API for MEMBER")
public class StoreLocationController {
  @Autowired
  private StoreLocationImp storeLocationImp;

  // ============================
  // 1. Lấy danh sách tất cả StoreLocation. (Get all store locations)
  // ============================

  @Operation(
          summary = "Get all store locations API",
          description = "MEMBER can get all store locations",
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
  @GetMapping()
//  @PreAuthorize("hasRole('MEMBER')")
  public ResponseEntity<?> getAllStoreLocations() {
    BaseResponse response =
            new BaseResponse(200, "success", storeLocationImp.getAllStoreLocations());
    return ResponseEntity.ok(response);
  }
}

