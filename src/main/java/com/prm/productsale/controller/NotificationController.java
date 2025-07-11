package com.prm.productsale.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.prm.productsale.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification API")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestParam int userId, @RequestParam String content) {
        notificationService.sendNotificationToUser(userId, content);
        return ResponseEntity.ok("Notification sent");
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyNotifications() {
        return ResponseEntity.ok(notificationService.getUserNotifications());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable int id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Marked as read");
    }
}