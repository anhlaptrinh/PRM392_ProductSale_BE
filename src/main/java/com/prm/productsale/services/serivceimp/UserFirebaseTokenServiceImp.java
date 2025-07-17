package com.prm.productsale.services.serivceimp;

import com.prm.productsale.entity.UserFirebaseToken;
import com.prm.productsale.repository.UserFirebaseTokenRepo;
import com.prm.productsale.services.UserFirebaseTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserFirebaseTokenServiceImp implements UserFirebaseTokenService {

    private final UserFirebaseTokenRepo tokenRepo;

    public UserFirebaseTokenServiceImp(UserFirebaseTokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void saveOrUpdateToken(Long userId, String token) {
        UserFirebaseToken userToken = tokenRepo.findByUserId(userId).orElse(new UserFirebaseToken());
        userToken.setUserId(userId);
        userToken.setFcmToken(token);
        userToken.setUpdatedAt(LocalDateTime.now());
        tokenRepo.save(userToken);
    }

    @Override
    public UserFirebaseToken getTokenByUserId(Long userId) {
        return tokenRepo.findByUserId(userId).orElse(null);
    }
}