package com.prm.productsale.controller;

import com.prm.productsale.dto.request.OrderRequest;
import com.prm.productsale.dto.request.ReorderRequest;
import com.prm.productsale.dto.response.*;
import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.CartItemEntity;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.entity.PaymentEntity;
import com.prm.productsale.repository.CartRepo;
import com.prm.productsale.repository.PaymentRepo;
import com.prm.productsale.services.MomoPaymentService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/orders")
@CrossOrigin
@Tag(name = "Order API", description = "API for user orders")
public class OrderController {
    @Autowired
    OrderImp orderService;
    @Autowired
    private MomoPaymentService momoPaymentService;
    @Autowired
    private PaymentRepo paymentRepo;
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
        OrderDetailResponse response = new OrderDetailResponse(
                order.getId(),
                order.getOrderStatus(),
                order.getPmMethod(),
                order.getBill(),
                order.getOrderDate(),
                order.getCart().getUser().getAddress(),
                order.getCart().getTotal()
        );
        return ResponseEntity.ok(BaseResponse.getResponse("Success", response));
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
        // Bước 1: Tạo OrderEntity
        OrderEntity createdOrder = orderService.createOrder(request);

        // Bước 2: Nếu MOMO thì gọi MomoPaymentService
        if ("MOMO".equalsIgnoreCase(request.getPaymentMethod())) {
            Map<String, Object> momoResult = momoPaymentService.createMomoPayment(createdOrder);

            String payUrl = (String) momoResult.get("payUrl");
            String qrCodeUrl = (String) momoResult.get("qrCodeUrl");

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentUrl(payUrl);
            paymentResponse.setQrCodeUrl(qrCodeUrl);
            paymentResponse.setOrderID(createdOrder.getId());

            // ✅ Sau khi tạo response, update cart thành INACTIVE
            orderService.markCartInactive(createdOrder.getCart());

            return ResponseEntity.ok(
                    BaseResponse.getResponse("Tạo thanh toán MOMO thành công", paymentResponse)
            );
        }

    // Bước 3: Nếu COD ➜ trả thông tin order
        PaymentSuccessResponse payment = new PaymentSuccessResponse();
        payment.setOrderID(createdOrder.getId());
        payment.setTotalAmount(createdOrder.getCart().getTotal());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus("PAID");
    // set PaymentEntity de luu vao DB
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderID(createdOrder.getId());
        paymentEntity.setTotalAmount(createdOrder.getCart().getTotal());
        paymentEntity.setPaymentDate(LocalDateTime.now());
        paymentEntity.setPaymentStatus("PAID");
        paymentRepo.save(paymentEntity);

        // ✅ Sau khi xử lý thanh toán COD xong, update cart thành INACTIVE
        orderService.markCartInactive(createdOrder.getCart());

        return ResponseEntity.ok(
                BaseResponse.getResponse("Tạo đơn hàng COD thành công", payment)
        );
    }

    @Operation(
            summary = "Update an existing order",
            description = "Update order Status by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable int id,@RequestParam String status) {
        orderService.updateOrder(id , status);
        return ResponseEntity.ok(BaseResponse.getResponse("Order updated", null));
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderHistoryResponse>> getOrderHistory(@PathVariable int userId) {
        List<OrderHistoryResponse> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @Operation(
            summary = "Get all cart items in order ",
            description = "Returns a list of all items in order placed by users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched orders successfully",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class)))
                    )
            }
    )
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<CartItemFlatResponse>> getOrderItems(@PathVariable int orderId) {
        List<CartItemEntity> items = orderService.getCartItemsByOrderId(orderId);
        List<CartItemFlatResponse> response = items.stream()
                .map(item -> new CartItemFlatResponse(
                        item.getId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getProduct().getId(),
                        item.getProduct().getProductName(),
                        item.getProduct().getImageURL()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reorder an order",
            description = "User can reorder an  order",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Order found"),
                    @ApiResponse(responseCode = "404", description = "Order not found")
            }
    )
    @PostMapping("/reorder")
    public ResponseEntity<?> reorder(@RequestBody ReorderRequest request) {

        orderService.reorder(request);
        return ResponseEntity.ok(BaseResponse.getResponse("Added to cart", true));
    }
}
