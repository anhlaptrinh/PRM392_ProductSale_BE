package com.prm.productsale.controller;

import com.prm.productsale.dto.request.OrderRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.services.serivceimp.OrderImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/orders")
@CrossOrigin
@Tag(name = "Order API", description = "API for user orders")
public class OrderController {
    @Autowired
    OrderImp orderService;
    @Operation(
            summary = "Get all orders",
            description = "Returns a list of all orders placed by users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched orders successfully",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> getAllOrders() {

        return ResponseEntity.ok(BaseResponse.getResponse("Success",orderService.getAll()));
    }

    @Operation(
            summary = "Get order by ID",
            description = "Get detailed info of a specific order by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Order found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable int id) {
        OrderEntity order = orderService.getOrder(id);
        return ResponseEntity.ok(BaseResponse.getResponse("Success", order));
    }

    @Operation(
            summary = "Create a new order",
            description = "MEMBER can place a new order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created order successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BaseResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input provided")
            }
    )
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        // Gọi Service tạo đơn:
        OrderEntity createdOrder = orderService.createOrder(request);

        // Sau đó trả về response chuẩn:
        BaseResponse response = BaseResponse.getResponse("Order created", createdOrder);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing order",
            description = "Update order details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable Long id,
            @RequestBody  OrderRequest request) {
        return ResponseEntity.ok("");
    }

    @Operation(
            summary = "Delete an order",
            description = "Delete a specific order by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order deleted"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {

        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Get order Status",
            description = "Get order Status",
            responses = {
        @ApiResponse(responseCode = "204", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    }
    )
    @GetMapping("/status")
    public ResponseEntity<?> getStatusOrder(@RequestParam String status){
        return ResponseEntity.ok(BaseResponse.getResponse("successfully",orderService.findByStatus(status)));
    }
    @Operation(
            summary = "Update order Status",
            description = "Update order Status",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order found"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam int id, @RequestParam String status){
        orderService.editStatus(id,status);
        return ResponseEntity.ok(BaseResponse.getResponse("Status change to: "+status,""));
    }

}
