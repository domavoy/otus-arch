package ru.mdorofeev.finance.core.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;

//TODO: P2: check is normal pattern to call externtal webservices ?
@Service
public class AuthIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Value("${app.integration.auth-service-rest.base}")
    private String authServiceBase;

    //TDOO: P2: improve logic
    public AuthServiceClient client(){
        return new AuthServiceClient(authServiceBase, restTemplate);
    }
}

