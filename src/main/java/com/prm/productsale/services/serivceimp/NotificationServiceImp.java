package com.prm.productsale.services.serivceimp;
import com.prm.productsale.entity.UserFirebaseToken;
import com.prm.productsale.services.UserFirebaseTokenService;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prm.productsale.services.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.prm.productsale.entity.NotificationEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.repository.NotificationRepo;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.messaging.*;

@Service
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final UserFirebaseTokenService tokenService;

    public NotificationServiceImp(NotificationRepo notificationRepo, UserFirebaseTokenService tokenService) {
        this.notificationRepo = notificationRepo;
        this.tokenService = tokenService;
    }

    @Override
    public void sendNotificationToToken(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveNotification(NotificationEntity notification) {
        notificationRepo.save(notification);
    }
    @Override
    public void sendCartBadgeUpdateNotification(Long userId, int cartCount) {
        Optional<UserFirebaseToken> userTokenOpt = Optional.ofNullable(tokenService.getTokenByUserId(userId));
        if (userTokenOpt.isPresent()) {
            String token = userTokenOpt.get().getFcmToken();
            String title = "Cập nhật giỏ hàng";
            String body = "Số lượng sản phẩm trong giỏ hàng: " + cartCount;

            sendNotificationToToken(token, title, body);
        } else {
            System.out.println("No FCM token found for userId: " + userId);
        }
    }
    @Override
    public List<NotificationEntity> getNotificationByUser(UserEntity user) {
        return notificationRepo.findByUser(user);
    }
}
