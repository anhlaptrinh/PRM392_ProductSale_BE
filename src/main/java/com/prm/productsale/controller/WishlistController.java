package com.prm.productsale.controller;


import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.dto.response.WishListResponse;
import com.prm.productsale.services.serivceimp.WishlistImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Wishlist API", description = "API for user Wishlist")
public class WishlistController {
    @Autowired
    private WishlistImp wishlistImp;
    @Operation(
            summary = "Add to Wishlist",
            description = "Member can Add their favorite items",
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
    @PostMapping
    public ResponseEntity<?> createWishlist(@RequestParam int id){
        wishlistImp.addToWishlist(id);
        return ResponseEntity.ok(BaseResponse.getResponse("Add Success",""));

    }

    @Operation(
            summary = "Get Wishlist",
            description = "Member can Get their Wishlist",
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
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<WishListResponse> listResponses = wishlistImp.findAll();
        return ResponseEntity.ok(BaseResponse.getResponse("Success",listResponses.isEmpty()?"empty data":
                listResponses));
    }
}
