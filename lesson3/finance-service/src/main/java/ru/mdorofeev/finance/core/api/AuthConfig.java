package ru.mdorofeev.finance.core.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;

@Configuration
public class AuthConfig {

    @Value("${app.integration.auth-service-rest-base}")
    private String authServiceBase;

    @Bean
    public AuthServiceClient authServiceClient() {
        return new AuthServiceClient(authServiceBase);
    }
}
