package com.prm.productsale.controller;

import com.prm.productsale.response.BaseResponse;
import com.prm.productsale.services.StoreLocationServicesImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/store-locations")
@CrossOrigin
@Tag(name = "Store Locations API", description = "API for Users")
public class StoreLocationController {
  @Autowired
  private StoreLocationServicesImp storeLocationServicesImp;

  // ============================
  // 1. Lấy danh sách tất cả StoreLocation. (Get all store locations)
  // ============================

  @Operation(
          summary = "Get all store locations API",
          description = "MEMBER can get all store locations",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Got all store locations successful",
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
    BaseResponse response = new BaseResponse();
    response.setCode(200);
    response.setMessage("Got all store locations successful");
    response.setData(storeLocationServicesImp.getAllStoreLocations());
    return ResponseEntity.ok(response);
  }
}
