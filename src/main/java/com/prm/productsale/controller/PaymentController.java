package com.prm.productsale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prm.productsale.dto.request.MomoIpnRequest;
import com.prm.productsale.dto.response.BaseResponse;
import com.prm.productsale.dto.response.PaymentResponse;
import com.prm.productsale.dto.response.PaymentSuccessResponse;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.OrderRepo;
import com.prm.productsale.services.MomoPaymentService;
import com.prm.productsale.services.OrderServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private MomoPaymentService momoService;

    @Autowired
    private OrderServices orderService;

    @Autowired
    private OrderRepo orderRepo;
    /**
     * Client gọi để lấy link thanh toán QR code MoMo
     */
    @Operation(summary = "Create MoMo Payment URL")
    @PostMapping("/momo-payment")
    public ResponseEntity<?> createMomoPayment(@RequestParam int orderId) {
        try {
            OrderEntity order = orderService.getOrder(orderId);
            Map<String, Object> momoResult = momoService.createMomoPayment(order);
            String payUrl = (String) momoResult.get("payUrl");
            String qrCodeUrl = (String) momoResult.get("qrCodeUrl");

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentUrl(payUrl);
            paymentResponse.setQrCodeUrl(qrCodeUrl);
            return ResponseEntity.ok(BaseResponse.getResponse("Payment URL and QR generated", paymentResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.getResponse("Error", e.getMessage()));
        }
    }

    /**
     * MoMo server gọi về IPN callback ➜ Xác minh thanh toán, update order
     */
    @Operation(summary = "Handle MoMo IPN Callback")
    @PostMapping("/momo-ipn")
    public ResponseEntity<?> handleMomoCallback(@RequestBody String rawJson) {
        System.out.println("🔔 MoMo IPN JSON (raw):\n" + rawJson);

        try {
            // Chuyển sang dùng DTO
            ObjectMapper objectMapper = new ObjectMapper();
            MomoIpnRequest request = objectMapper.readValue(rawJson, MomoIpnRequest.class);
            momoService.handleCallback(request);
            return ResponseEntity.ok("IPN received successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("IPN failed: " + e.getMessage());
        }
    }

    /**
     * (Tùy chọn) Redirect user về site thành công
     */
    @Operation(summary = "Check Payment Status by Order ID")
    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable int orderId) {
        try {
            PaymentSuccessResponse response = momoService.getPaymentStatus(orderId);
            return ResponseEntity.ok(response);
        } catch (AppException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Lỗi xử lý thanh toán: " + ex.getMessage());
        }
    }
}
