package ru.mdorofeev.finance.core.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.core.exception.ServiceException;

//TODO: P2: check is normal pattern to call externtal webservices ?
@Service
public class AuthIntegrationService {

    @Value("${app.integration.auth-service-rest.base}")
    private String authServiceBase;

    @Value("${app.integration.auth-service-rest.uri.create-user}")
    private String authServiceCreateUserUri;

    @Value("${app.integration.auth-service-rest.uri.create-session}")
    private String authServiceCreateUserSession;

    @Value("${app.integration.auth-service-rest.uri.get-user-by-session}")
    private String authGetUserBySessionUri;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public void createUser(String login, String password) throws ServiceException {
        UserData userData = new UserData(login, password);
        Response result = restTemplate.postForEntity(authServiceBase + authServiceCreateUserUri, userData, Response.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
    }

    public Long createSession(String login, String password) throws ServiceException {
        UserData userData = new UserData(login, password);
        Session result = restTemplate.postForEntity(authServiceBase + authServiceCreateUserSession, userData, Session.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }

        return Long.valueOf(result.getSessionId());
    }

    public Long findBySession(Long sessionId) throws ServiceException {
        LongResponse result = restTemplate.getForEntity(authServiceBase + authGetUserBySessionUri + sessionId, LongResponse.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
        return result.getResult();
    }
}

