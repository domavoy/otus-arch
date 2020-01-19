package ru.mdorofeev.finance.core.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.core.exception.ServiceException;

import java.util.Arrays;

//TODO: P2: check is normal pattern to call externtal webservices ?
@Service
public class AuthIntegrationService {

    @Value("${app.integration.auth-service-get-user-by-session-url}")
    private String authGetUserBySession;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public Long findBySession(Long sessionId) throws ServiceException {
        LongResponse result = restTemplate.getForEntity(authGetUserBySession + sessionId, LongResponse.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
        return result.getResult();
    }
}

