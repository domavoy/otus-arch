package ru.mdorofeev.finance.core.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;

import java.util.UUID;

@Disabled
@ActiveProfiles({"db-h2mem"})
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.auth-service-rest-base=http://localhost:8081/auth",
})
public class AuthIntegrationTest {

    @Value("${app.integration.auth-service-rest-base}")
    private String authServiceBase;

    @Test
    public void test() throws ServiceException {
        String login = UUID.randomUUID().toString();

        AuthServiceClient client = new AuthServiceClient(authServiceBase);
        client.createUser(login, login);
        Long session = client.createSession(login, login);
        Long data = client.findBySession(session);
        Assertions.assertNotNull(data, "getUserBySession");
    }


//     System.getProperties().put( "server.port", 8081);
//        new SpringApplicationBuilder().sources(FinanceApplication .class).
//    listeners((ApplicationListener<ApplicationReadyEvent>) event -> {
//
//    }).run();

}
