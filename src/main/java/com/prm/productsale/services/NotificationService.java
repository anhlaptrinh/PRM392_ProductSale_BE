package com.prm.productsale.services;

import com.prm.productsale.entity.NotificationEntity;
import java.util.List;

public interface NotificationService {
    void sendNotificationToUser(int userId, String content);
    List<NotificationEntity> getUserNotifications();
    void markAsRead(int notificationId);
}