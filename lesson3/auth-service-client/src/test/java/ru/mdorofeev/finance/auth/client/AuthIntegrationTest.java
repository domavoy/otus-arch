package ru.mdorofeev.finance.auth.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;

import java.util.UUID;


public class AuthIntegrationTest {

    @Test
    public void test() throws ServiceException {
        String login = UUID.randomUUID().toString();

        AuthServiceClient client = new AuthServiceClient("http://localhost:8080/auth");
        client.createUser(login, login);
        Long session = client.createSession(login, login);
        Long data = client.findBySession(session);
        Assertions.assertNotNull(data, "getUserBySession");

        Assertions.assertTrue(client.checkUserId(data));
        Assertions.assertFalse(client.checkUserId(4242L));
    }


//     System.getProperties().put( "server.port", 8081);
//        new SpringApplicationBuilder().sources(FinanceApplication .class).
//    listeners((ApplicationListener<ApplicationReadyEvent>) event -> {
//
//    }).run();

}
