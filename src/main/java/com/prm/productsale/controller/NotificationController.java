package com.prm.productsale.controller;

import com.prm.productsale.dto.request.NotificationRequest;
import com.prm.productsale.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendNotificationToToken(
                request.getFcmToken(), request.getTitle(), request.getBody()
        );
        return ResponseEntity.ok("Notification sent");
    }
}
