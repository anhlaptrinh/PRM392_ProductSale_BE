package com.prm.productsale.config;
import com.prm.productsale.services.NotificationService;
import com.prm.productsale.services.UserFirebaseTokenService;
import com.prm.productsale.entity.UserFirebaseToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import com.prm.productsale.services.CartServices;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NotificationScheduler {
    private final NotificationService notificationService;
    private final UserFirebaseTokenService tokenService;
    private final CartServices cartServices;
    public NotificationScheduler(NotificationService notificationService, UserFirebaseTokenService tokenService,CartServices cartServices) {
        this.notificationService = notificationService;
        this.tokenService = tokenService;
        this.cartServices = cartServices;

    }

    // Gửi notification mỗi 5 phút
    @Scheduled(fixedRate = 10000) // mỗi 5 phút
    public void sendCartReminderNotification() {
        List<UserFirebaseToken> allTokens = tokenService.getAllTokens();

        for (UserFirebaseToken userToken : allTokens) {
            Long userId = userToken.getUserId();
            String token = userToken.getFcmToken();

            // Gọi đến CartService để lấy số lượng sản phẩm trong giỏ
            int cartCount = cartServices.getItemCountByUserId(userId);

            if (cartCount > 0) {
                String body = "Bạn có " + cartCount + " sản phẩm trong giỏ hàng lúc " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String title = "🛒 Nhắc nhở giỏ hàng";

                notificationService.sendNotificationToToken(token, title, body);
            }
        }

        System.out.println("✅ Đã gửi notification nhắc giỏ hàng đến users có sản phẩm trong giỏ.");
    }
}
