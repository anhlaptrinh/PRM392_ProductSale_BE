package com.prm.productsale.services;
import com.prm.productsale.entity.UserFirebaseToken;
import java.util.List;

public interface UserFirebaseTokenService {
    void saveOrUpdateToken(Long userId, String token);
    UserFirebaseToken getTokenByUserId(Long userId);
    List<UserFirebaseToken> getAllTokens();

}
