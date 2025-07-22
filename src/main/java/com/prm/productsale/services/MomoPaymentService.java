package com.prm.productsale.services;

import com.prm.productsale.config.MomoConfig;
import com.prm.productsale.dto.request.MomoIpnRequest;
import com.prm.productsale.dto.response.PaymentSuccessResponse;
import com.prm.productsale.entity.OrderEntity;
import com.prm.productsale.entity.PaymentEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.OrderRepo;
import com.prm.productsale.repository.PaymentRepo;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MomoPaymentService {

    @Autowired
    private MomoConfig momoConfig;

    @Autowired
    private OrderServices orderService;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentRepo paymentRepo;

    public Map<String, Object> createMomoPayment(OrderEntity order) {
        String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";

        // ✅ Lấy key từ config cho chuẩn
        String partnerCode = momoConfig.getPartnerCode();
        String accessKey = momoConfig.getAccessKey();
        String secretKey = momoConfig.getSecretKey();
        String ipn = momoConfig.getIpnUrl();

        // ✅ Ép amount thành số nguyên VNĐ
        BigDecimal total = order.getCart().getTotal();
        String amount = String.valueOf(total.intValue());  // Ví dụ 50.00 ➜ 50

        String requestId = UUID.randomUUID().toString();
        String orderId = order.getId() + "-" + System.currentTimeMillis();

        // ✅ RawSignature phải chuẩn thứ tự và giá trị
        String rawSignature = "accessKey=" + accessKey
                + "&amount=" + amount
                + "&extraData="
                + "&ipnUrl=" + ipn
                + "&orderId=" + orderId
                + "&orderInfo=Thanh toan don hang"
                + "&partnerCode=" + partnerCode
                + "&redirectUrl=https://yourdomain.com/payment-success"
                + "&requestId=" + requestId
                + "&requestType=captureWallet";

        String signature = hmacSHA256(rawSignature, secretKey);

        // ✅ Log mọi thứ để debug
        System.out.println("=== MoMo Payment Request ===");
        System.out.println("Order ID: " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Raw Signature: " + rawSignature);
        System.out.println("Signature: " + signature);

        // ✅ Build body khớp từng giá trị
        Map<String, Object> body = new HashMap<>();
        body.put("partnerCode", partnerCode);
        body.put("accessKey", accessKey);
        body.put("requestId", requestId);
        body.put("amount", amount);
        body.put("orderId", orderId);
        body.put("orderInfo", "Thanh toan don hang");
        body.put("redirectUrl", "https://yourdomain.com/payment-success");
        body.put("ipnUrl", ipn);
        body.put("requestType", "captureWallet");
        body.put("extraData", "");
        body.put("signature", signature);

        System.out.println("Request Body: " + body);

        // ✅ Gửi request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, entity, Map.class);

        System.out.println("=== MoMo Payment Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> resBody = response.getBody();
            if (resBody != null && resBody.containsKey("payUrl")) {
                String payUrl = resBody.get("payUrl").toString();
                String qrCodeUrl = "";
                if (resBody.containsKey("qrCodeUrl")) {
                    qrCodeUrl = resBody.get("qrCodeUrl").toString();
                }
                Map<String, Object> result = new HashMap<>();
                result.put("payUrl", payUrl);
                result.put("qrCodeUrl", qrCodeUrl);
                return result;
            } else {
                throw new RuntimeException("MoMo response missing payUrl: " + resBody);
            }
        }

        throw new RuntimeException("Failed to create MoMo payment: " + response.getBody());
    }

    public void handleCallback(MomoIpnRequest callback) {
        String orderIdStr = callback.getOrderId();
        String resultCode = String.valueOf(callback.getResultCode());
        String receivedSignature = callback.getSignature();

        if (orderIdStr == null || resultCode == null || receivedSignature == null) {
            throw new RuntimeException("Invalid callback data");
        }

        String rawData = "accessKey=" + momoConfig.getAccessKey()
                + "&amount=" + callback.getAmount()
                + "&extraData=" + callback.getExtraData()
                + "&message=" + callback.getMessage()
                + "&orderId=" + callback.getOrderId()
                + "&orderInfo=" + callback.getOrderInfo()
                + "&orderType=" + callback.getOrderType()
                + "&partnerCode=" + callback.getPartnerCode()
                + "&payType=" + callback.getPayType()
                + "&requestId=" + callback.getRequestId()
                + "&responseTime=" + callback.getResponseTime()
                + "&resultCode=" + callback.getResultCode()
                + "&transId=" + callback.getTransId();

        String serverSignature = hmacSHA256(rawData, momoConfig.getSecretKey());

        if (!serverSignature.equals(receivedSignature)) {
            throw new RuntimeException("Invalid MoMo signature!");
        }

        // ✅ Xử lý đơn hàng
        int orderId = Integer.parseInt(orderIdStr.split("-")[0]);
        OrderEntity order = orderService.getOrder(orderId);
        if (callback.getResultCode() == 0) {

            PaymentEntity payment = new PaymentEntity();
            payment.setOrderID(orderId);
            payment.setTotalAmount(order.getCart().getTotal());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentStatus("PAID");

            paymentRepo.save(payment);

            System.out.println("✅ Order " + orderId + " đã thanh toán và đang giao hàng");
        } else {
            orderService.editStatus(orderId, "FAILED");
            System.out.println("❌ Order " + orderId + " thất bại");
        }
    }


    private String hmacSHA256(String data, String key) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKeySpec);
            byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign: " + e.getMessage());
        }
    }
    public PaymentSuccessResponse getPaymentStatus(int orderId) {
        PaymentEntity payment = paymentRepo.findByOrderID(orderId);

        if (payment == null) {
            throw new AppException(ErrorCode.ORDER_NOT_EXIST); // hoặc tự định nghĩa lỗi "Payment Not Found"
        }

        if (!"PAID".equals(payment.getPaymentStatus())) {
            throw new AppException(ErrorCode.INVALID_FORMAT); // hoặc định nghĩa "PAYMENT_NOT_COMPLETE"
        }

        PaymentSuccessResponse response = new PaymentSuccessResponse();
        response.setOrderID(payment.getOrderID());
        response.setTotalAmount(payment.getTotalAmount());
        response.setPaymentDate(payment.getPaymentDate());
        response.setPaymentStatus(payment.getPaymentStatus());

        return response;
    }
}
