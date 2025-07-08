package com.prm.productsale.controller;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.services.serivceimp.CartImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mobile/cart")
@Tag(name = "Cart API", description = "API for Cart")

public class CartController {
    @Autowired
    CartImp cartImp;

    @Operation(
            summary = "Create a cart Item",
            description = "Member can create a cart Item",
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
    public ResponseEntity<?> createCartItem(@RequestBody CartItemRequest request){
        cartImp.createItem(request);
        return ResponseEntity.ok(BaseResponse.getResponse("Add Success",""));
    }
    @Operation(
            summary = "Get cart Items",
            description = "Member can get a cart Item",
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
    @GetMapping()
    public ResponseEntity<?> getCartItem(){
        return ResponseEntity.ok(BaseResponse.getResponse("successfully",cartImp.getListItem().isEmpty()?"":cartImp.getListItem()));
    }
    @Operation(
            summary = "Update Quantity cart Items",
            description = "Member can edit number cart Item",
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
    @PutMapping("/{id}/quantity")
    public ResponseEntity<?> updateQuantityCartItem(@PathVariable("id") int cartItemId,
                                                    @RequestParam int quantity){
        return ResponseEntity.ok(BaseResponse.getResponse("successfully",cartImp.updateItem(cartItemId,quantity)));
    }

    @Operation(summary = "Delete item cart")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") int cartItemId) {
        cartImp.deleteItem(cartItemId);
        return ResponseEntity.ok(BaseResponse.getResponse("Success",""));
    }
}
