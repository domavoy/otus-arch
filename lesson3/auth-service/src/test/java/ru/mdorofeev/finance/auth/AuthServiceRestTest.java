package ru.mdorofeev.finance.auth;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.auth.api.AuthControllerImpl;
import ru.mdorofeev.finance.auth.api.model.common.Session;
import ru.mdorofeev.finance.auth.api.model.request.UserData;
import ru.mdorofeev.finance.common.api.model.response.BooleanResponse;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;

@ActiveProfiles("db-h2mem")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthServiceRestTest {

    @Autowired
    private AuthControllerImpl controller;

    @Test
    public void fullTest(){
        UserData user = new UserData("user", "password");
        ResponseEntity<Response> createUserResponse = controller.createUser(user);
        checkStatusCode(createUserResponse);

        ResponseEntity<BooleanResponse> checkUserResponse = controller.checkUser(user);
        checkStatusCode(checkUserResponse);
        Assert.assertEquals("check user", true, checkUserResponse.getBody().getResult());

        ResponseEntity<Session> sessionResponse = controller.createSession(user);
        String sessionId = sessionResponse.getBody().getSessionId();
        checkStatusCode(sessionResponse);
        Assert.assertNotNull("Create sessionId", sessionId);

        ResponseEntity<BooleanResponse> checkSessionResponse = controller.checkSession(Long.valueOf(sessionId));
        checkStatusCode(checkSessionResponse);
        Assert.assertEquals("Check sessionId", true, checkSessionResponse.getBody().getResult());

        ResponseEntity<LongResponse> getUserResponse = controller.getUserBySession(Long.valueOf(sessionId));
        checkStatusCode(getUserResponse);
        Assert.assertNotNull("check user session", getUserResponse.getBody().getResult());
    }

    @Test
    public void userNotExists(){
        UserData user = new UserData("user1", "password1");

        ResponseEntity<BooleanResponse> checkUserResponse = controller.checkUser(user);
        checkStatusCode(checkUserResponse);
        Assert.assertEquals("check user: not exists", false, checkUserResponse.getBody().getResult());
    }

    @Test
    public void sessionNotExists(){
        ResponseEntity<BooleanResponse> checkSessionResponse = controller.checkSession(12345678L);
        checkStatusCode(checkSessionResponse);
        Assert.assertEquals("Check sessionId: not exists", false, checkSessionResponse.getBody().getResult());
    }

    @Test
    public void getUserSessionNotExists(){
        ResponseEntity checkSessionResponse = controller.getUserBySession(123456783L);
        Response response = ((Response)checkSessionResponse.getBody());

        checkStatusCode(checkSessionResponse);
        Assert.assertEquals("Create sessionId: user not founnd", "USER_NOT_FOUND", response.getError().getMessage());
    }

    @Test
    public void createSessionWithBadUser(){
        UserData user = new UserData("user123", "password123");
        ResponseEntity sessionResponse = controller.createSession(user);
        Response response = ((Response)sessionResponse.getBody());

        checkStatusCode(sessionResponse);
        Assert.assertEquals("Create sessionId: user not found", "USER_NOT_FOUND", response.getError().getMessage());
    }


    //TODO: P2: to common ?
    private void checkStatusCode(ResponseEntity responseEntity){
        Assert.assertEquals("create user Http status", 200, responseEntity.getStatusCode().value());
    }
}
