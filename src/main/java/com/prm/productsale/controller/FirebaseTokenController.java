package com.prm.productsale.controller;

import com.prm.productsale.services.UserFirebaseTokenService;
import com.prm.productsale.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseTokenController {

    @Autowired
    private UserFirebaseTokenService tokenService;

    @Autowired
    private LoginServices loginServices;

    @PostMapping("/register-token")
    public ResponseEntity<?> registerToken(@RequestParam String token) {
        Long userId = (long) loginServices.getUser().getId();
        tokenService.saveOrUpdateToken(userId, token);
        return ResponseEntity.ok("Token saved");
    }

    @GetMapping("/my-token")
    public ResponseEntity<?> getMyToken() {
        Long userId = (long) loginServices.getUser().getId();
        return ResponseEntity.ok(tokenService.getTokenByUserId(userId));
    }
}
