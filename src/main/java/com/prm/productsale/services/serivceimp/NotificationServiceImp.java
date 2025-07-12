package com.prm.productsale.services.serivceimp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prm.productsale.repository.NotificationRepo;
import com.prm.productsale.repository.UserRepo;
import com.prm.productsale.services.NotificationService;
import com.prm.productsale.services.LoginServices;
import com.prm.productsale.entity.NotificationEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {
    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginServices loginServices;

    @Override
    public void sendNotificationToUserByEmail(String email, String content) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        NotificationEntity notification = new NotificationEntity();
        notification.setContent(content);
        notification.setUser(user);
        notificationRepo.save(notification);
    }


    @Override
    public List<NotificationEntity> getUserNotifications() {
        int userId = loginServices.getUser().getId();
        return notificationRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }
    @Override
    public List<NotificationEntity> getAllNotifications() {
        List<UserEntity> users = userRepo.findAll();

        List<NotificationEntity> allNotifications = new ArrayList<>();

        for (UserEntity user : users) {
            allNotifications.addAll(user.getNotifications()); // cần getNotifications()
        }

        allNotifications.sort(Comparator.comparing(NotificationEntity::getCreatedAt).reversed());

        return allNotifications;
    }


    @Override
    public void markAsRead(int notificationId) {
        NotificationEntity notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));
        notification.setRead(true);
        notificationRepo.save(notification);
    }
}
