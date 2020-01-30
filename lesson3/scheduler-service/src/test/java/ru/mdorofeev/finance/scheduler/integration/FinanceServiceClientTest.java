package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionListResponse;

import java.util.UUID;

@Disabled
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.finance-service-rest-base=http://localhost:8080",
        "app.integration.auth-service-rest-base=http://localhost:8081/auth",
})
public class FinanceServiceClientTest {

    @Value("${app.integration.finance-service-rest-base}")
    private String financeServiceBase;

    @Value("${app.integration.auth-service-rest-base}")
    private String authServiceBase;

    @Test
    public void test() throws ServiceException {
        String login = UUID.randomUUID().toString();

        AuthServiceClient client = new AuthServiceClient(authServiceBase);
        client.createUser(login, login);
        Long session = client.createSession(login, login);

        FinanceServiceClient financeClient = new FinanceServiceClient(financeServiceBase);
        financeClient.createAccount(session, "account");
        financeClient.createCategory(session, "category");
        financeClient.createTransaction(session, "category", "account", 100.0, "100");
        TransactionListResponse trs = financeClient.getTransactions("2010-10-10", session);
        Assertions.assertNotNull(trs);

        financeClient.createCurrency( login, 23.0, session);
        Double cur = financeClient.getCurrency( login, session);
        Assertions.assertEquals(23.0, cur);

        financeClient.updateCurrency( login, 24.0, session);
        cur = financeClient.getCurrency( login, session);
        Assertions.assertEquals(24.0, cur);
    }
}
