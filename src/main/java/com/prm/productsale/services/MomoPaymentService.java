package com.prm.productsale.services;

import com.prm.productsale.config.MomoConfig;
import com.prm.productsale.entity.OrderEntity;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MomoPaymentService {

    @Autowired
    private MomoConfig momoConfig;

    @Autowired
    private OrderServices orderService;

    public Map<String, Object> createMomoPayment(OrderEntity order) {
        String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";

        // ✅ Lấy key từ config cho chuẩn
        String partnerCode = momoConfig.getPartnerCode();
        String accessKey = momoConfig.getAccessKey();
        String secretKey = momoConfig.getSecretKey();

        // ✅ Ép amount thành số nguyên VNĐ
        BigDecimal total = order.getCart().getTotal();
        String amount = String.valueOf(total.intValue());  // Ví dụ 50.00 ➜ 50

        String requestId = UUID.randomUUID().toString();
        String orderId = order.getId() + "-" + System.currentTimeMillis();

        // ✅ RawSignature phải chuẩn thứ tự và giá trị
        String rawSignature = "accessKey=" + accessKey
                + "&amount=" + amount
                + "&extraData="
                + "&ipnUrl=https://yourdomain.com/api/orders/momo/callback"
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
        body.put("ipnUrl", "https://yourdomain.com/api/orders/momo/callback");
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

    public void handleCallback(Map<String, String> callbackParams) {
        String orderIdStr = callbackParams.get("orderId");
        String resultCode = callbackParams.get("resultCode");
        String receivedSignature = callbackParams.get("signature");

        if (orderIdStr == null || resultCode == null || receivedSignature == null) {
            throw new RuntimeException("Invalid callback data");
        }

        String rawData = "accessKey=" + callbackParams.get("accessKey")
                + "&amount=" + callbackParams.get("amount")
                + "&extraData=" + callbackParams.get("extraData")
                + "&message=" + callbackParams.get("message")
                + "&orderId=" + callbackParams.get("orderId")
                + "&orderInfo=" + callbackParams.get("orderInfo")
                + "&orderType=" + callbackParams.get("orderType")
                + "&partnerCode=" + callbackParams.get("partnerCode")
                + "&payType=" + callbackParams.get("payType")
                + "&requestId=" + callbackParams.get("requestId")
                + "&responseTime=" + callbackParams.get("responseTime")
                + "&resultCode=" + callbackParams.get("resultCode")
                + "&transId=" + callbackParams.get("transId");

        String serverSignature = hmacSHA256(rawData, momoConfig.getSecretKey());

        if (!serverSignature.equals(receivedSignature)) {
            throw new RuntimeException("Invalid MoMo signature!");
        }

        int orderId = Integer.parseInt(orderIdStr.split("-")[0]);
        if ("0".equals(resultCode)) {
            orderService.editStatus(orderId, "PAID");
            OrderEntity order = orderService.getOrder(orderId);
            int userId = order.getUser().getId();
            // orderService.clearCartByUserId(userId); // nếu có
            System.out.println("Order " + orderId + " PAID");
        } else {
            orderService.editStatus(orderId, "FAILED");
            System.out.println("Order " + orderId + " FAILED");
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
}
