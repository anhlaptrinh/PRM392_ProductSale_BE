package com.prm.productsale.services;
import java.util.List;
import com.prm.productsale.entity.NotificationEntity;
import com.prm.productsale.entity.UserEntity;
public interface NotificationService {
    void sendNotificationToToken(String token, String title, String body);
    void saveNotification(NotificationEntity notification);
    List<NotificationEntity> getNotificationByUser(UserEntity user);
    void sendCartBadgeUpdateNotification(Long userId, Integer cartCount);

}