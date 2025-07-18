package com.prm.productsale.services;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.prm.productsale.entity.CartEntity;
import com.prm.productsale.entity.UserEntity;
import com.prm.productsale.exception.AppException;
import com.prm.productsale.exception.ErrorCode;
import com.prm.productsale.repository.CartRepo;
import com.prm.productsale.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@Slf4j
public class LoginServices  {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  CartRepo cartRepo;
  @Value("${jwt.key}")
  private String keyjwt;

  public String login(String email, String password){
    SecurityContextHolder.clearContext();
    String token="";
    Optional<UserEntity> user = userRepo.findByEmail(email);
    if(user.isPresent()){
      UserEntity userEntity = user.get();

      if(passwordEncoder.matches(password, userEntity.getPassword()))
        token = generateToken(userEntity);
    }
    return token;
  }

  public String generateToken(UserEntity user)  {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .subject(user.getEmail())
            .issuer("productsale.com")
            .issueTime(new Date())
            .expirationTime(new Date(
                    Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
            ))
            .claim("scope",buildScope(user))
            .build();
    Payload payload = new Payload(jwtClaimsSet.toJSONObject());
    JWSObject jwsObject = new JWSObject(header,payload);
    try {
      jwsObject.sign(new MACSigner(keyjwt.getBytes()));
      return jwsObject.serialize();
    } catch (JOSEException e) {
      throw new RuntimeException(e);
    }
  }

  private String buildScope(UserEntity user){
    StringJoiner stringJoiner = new StringJoiner("");
    if (user.getRole() != null ) {
      stringJoiner.add(user.getRole()); // Thêm tên role của user vào chuỗi
    }
    return stringJoiner.toString();
  }

  public UserEntity getUser() {
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();
    return userRepo.findByEmail(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
  }
  public CartEntity getCart(){
    UserEntity user = getUser();
    if (user == null) {
      throw new IllegalStateException("No authenticated user found.");
    }
    List<CartEntity> activeCarts = cartRepo.findByUser_IdAndStatus(user.getId(), "ACTIVE");

    if (!activeCarts.isEmpty()) {
      if (activeCarts.size() > 1) {
        // Bạn có thể log hoặc xử lý tùy ý
        System.out.println("⚠ Warning: Có nhiều hơn 1 cart ACTIVE cho user ID " + user.getId());
      }

      return activeCarts.get(0); // lấy cái đầu tiên (hoặc thêm logic chọn cart gần nhất)
    }
    CartEntity cartEntity = new CartEntity();
    cartEntity.setUser(user);
    cartEntity.setTotal(BigDecimal.ZERO);
    cartEntity.setStatus("ACTIVE");
    cartRepo.save(cartEntity);
    return cartEntity;

  }

}

