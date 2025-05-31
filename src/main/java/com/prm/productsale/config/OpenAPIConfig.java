package com.prm.productsale.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API-Product Sale ",
                version = "1.0",
                description = "REST API description...",

                contact = @Contact(name = "Your Name", email = "your.email@example.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server")
        },

        security = {@SecurityRequirement(name = "bearerToken")}


)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})
public class OpenAPIConfig {
        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("api-service-1")
                        .packagesToScan("com.prm.productsale.controller") // Thay bằng package của bạn
                        .build();
        }
}
