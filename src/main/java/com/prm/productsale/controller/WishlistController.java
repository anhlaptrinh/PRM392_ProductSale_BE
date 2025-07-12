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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Wishlist API", description = "API for user Wishlist")
@CrossOrigin
@RequestMapping(value = "/api/mobile/wishlist")
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
            description = "Admin can Get All Wishlist",
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
    @GetMapping("/find-by-UserId")
    public ResponseEntity<?> findWishlistByUserId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<WishListResponse> items = wishlistImp.findByUserId(page,size);
        Map<String,Object> response = new HashMap<>();
        response.put("page",items.getNumber());
        response.put("totalPages",items.getTotalPages());
        response.put("totalItems",items.getTotalElements());
        response.put("isLast",items.isLast());
        response.put("wishListItem",items.getContent());
        return ResponseEntity.ok(BaseResponse.getResponse("Successfully",response));
    }
    @Operation(summary = "Delete item wishlist or create cart")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id,
                                        @RequestParam(defaultValue = "false") Boolean isCreateCart){
        wishlistImp.deleteWishList(id,isCreateCart);
        String message = isCreateCart==true?"Create Cart Success":"Delete Success";

        return ResponseEntity.ok(BaseResponse.getResponse("Success",message));

    }
}
