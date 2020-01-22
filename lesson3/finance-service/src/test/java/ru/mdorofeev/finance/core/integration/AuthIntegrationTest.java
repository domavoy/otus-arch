package ru.mdorofeev.finance.core.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.integration.AuthIntegrationService;

import java.util.UUID;

@Disabled
@ActiveProfiles({"db-h2mem"})
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.auth-service-rest.base=http://localhost:8081/auth",
})
public class AuthIntegrationTest {

    @Autowired
    private AuthIntegrationService authIntegrationService;

    @Test
    public void test() throws ServiceException {
        String login = UUID.randomUUID().toString();
        authIntegrationService.createUser(login, login);
        Long session = authIntegrationService.createSession(login, login);
        Long data = authIntegrationService.findBySession(session);
        Assertions.assertNotNull(data, "getUserBySession");
    }
}
