package com.prm.productsale.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirebaseInitializer {
    @PostConstruct
    public void initialize() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ClassPathResource("productsaleprm392-60b46-firebase-adminsdk-fbsvc-0a2e04e6cd.json").getInputStream()))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("FirebaseApp initialized");
        }
    }
}

