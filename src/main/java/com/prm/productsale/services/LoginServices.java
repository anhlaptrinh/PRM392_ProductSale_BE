package com.prm.productsale.services;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.RoleRepo;
import com.swd.pregnancycare.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
  private RoleRepo roleRepo;
  @Value("${jwt.key}")
  private String keyjwt;

  public String login(String email, String password){
    SecurityContextHolder.clearContext();
    String token="";
    Optional<UserEntity> user = userRepo.findByEmailAndStatusTrue(email);
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
            .issuer("pregnancy.com")
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
    if (user.getRole() != null && user.getRole().getName() != null) {
      stringJoiner.add(user.getRole().getName()); // Thêm tên role của user vào chuỗi
    }
    return stringJoiner.toString();
  }

  public  UserEntity getUser(){
    var context = SecurityContextHolder.getContext();
    String name = context.getAuthentication().getName();
    return userRepo.findByEmailAndStatusTrue(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
  }

}

