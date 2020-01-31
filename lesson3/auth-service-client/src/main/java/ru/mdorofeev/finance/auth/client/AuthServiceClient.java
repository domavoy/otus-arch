package ru.mdorofeev.finance.auth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.api.LongResponse;
import ru.mdorofeev.finance.auth.client.api.Response;
import ru.mdorofeev.finance.auth.client.api.Session;
import ru.mdorofeev.finance.auth.client.api.UserData;
import ru.mdorofeev.finance.common.api.model.response.BooleanResponse;
import ru.mdorofeev.finance.common.exception.ServiceException;

//TODO: P2: check is normal pattern to call externtal webservices ?
public class AuthServiceClient {

    private final String authServiceBase;
    private RestTemplate restTemplate;

    private String authServiceCreateUserUri = "/createUser";
    private String authServiceCreateUserSession = "/createSession";
    private String authGetUserBySessionUri = "/getUserBySession/";
    private String authCheckUserIdUri = "/checkUserId/{userId}";

    public AuthServiceClient(String authServiceBase) {
        this.authServiceBase = authServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
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

    public Boolean checkUserId(Long userId) throws ServiceException {
        BooleanResponse result = restTemplate.getForEntity(authServiceBase + authCheckUserIdUri,
                BooleanResponse.class, userId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
        return result.getResult();
    }
}
