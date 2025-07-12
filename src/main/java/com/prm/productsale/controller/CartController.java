package com.prm.productsale.controller;

import com.prm.productsale.dto.request.CartItemRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.dto.response.CartItemResponse;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.services.serivceimp.CartImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/mobile/cart")
@CrossOrigin
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
    @PreAuthorize("hasRole('MEMBER')")
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
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getCartItem( @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size){
        Page<CartItemResponse> items= cartImp.getListItem(page,size);
        BigDecimal total = cartImp.getTotalAmount();
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("cartItem", items.getContent());
        response.put("page", items.getNumber());
        response.put("totalPages", items.getTotalPages());
        response.put("totalItems", items.getTotalElements());
        response.put("isLast", items.isLast());

        return ResponseEntity.ok(BaseResponse.getResponse("successfully",response));
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
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateQuantityCartItem(@PathVariable("id") int cartItemId,
                                                    @RequestParam int quantity){
        return ResponseEntity.ok(BaseResponse.getResponse("successfully",cartImp.updateItem(cartItemId,quantity)));
    }

    @Operation(summary = "Delete item cart")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") int cartItemId) {
        cartImp.deleteItem(cartItemId);
        return ResponseEntity.ok(BaseResponse.getResponse("Success",""));
    }
    @Operation(summary = "Delete All item cart")
    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteAll() {
        cartImp.deleteAll();
        return ResponseEntity.ok(BaseResponse.getResponse("Success",""));
    }
}
