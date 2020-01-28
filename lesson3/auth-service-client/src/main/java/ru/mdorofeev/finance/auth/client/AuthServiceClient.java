package ru.mdorofeev.finance.auth.client;

import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.api.LongResponse;
import ru.mdorofeev.finance.auth.client.api.Response;
import ru.mdorofeev.finance.auth.client.api.Session;
import ru.mdorofeev.finance.auth.client.api.UserData;
import ru.mdorofeev.finance.common.exception.ServiceException;

public class AuthServiceClient {

    private final String authServiceBase;
    private final RestTemplate restTemplate;

    private String authServiceCreateUserUri = "/createUser";
    private String authServiceCreateUserSession = "/createSession";
    private String authGetUserBySessionUri = "/getUserBySession/";

    public AuthServiceClient(String authServiceBase, RestTemplate restTemplate) {
        this.authServiceBase = authServiceBase;
        this.restTemplate = restTemplate;
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
