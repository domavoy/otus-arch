package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.scheduler.integration.api.RepeatedPaymentResponse;

import java.util.UUID;

@Disabled
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.repeated-payment-service-config-rest-base=http://localhost:8082/repeatedPayment",
        "app.integration.auth-service-rest-base=http://localhost:8081/auth",
})
public class RepeatedServiceClientTest {

    @Value("${app.integration.repeated-payment-service-config-rest-base}")
    private String repeatedServiceBase;

    @Value("${app.integration.auth-service-rest-base}")
    private String authServiceBase;

    @Test
    public void test() throws ServiceException {
        String login = UUID.randomUUID().toString();

        AuthServiceClient cl = new AuthServiceClient(authServiceBase);
        cl.createUser(login, login);
        Long session = cl.createSession(login, login);

        RepeatedServiceClient client = new RepeatedServiceClient(repeatedServiceBase);
        client.addScheduledData(session, 100L, 100.0, "2020-10-10");
        RepeatedPaymentResponse data = client.getScheduledDataForUser(session, DateUtil.date("2020-10-10"));
        Assertions.assertEquals(1, data.getRepeatedPaymentList().size());
    }
}
