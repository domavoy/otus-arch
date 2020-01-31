package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.scheduler.integration.api.RepeatedPaymentResponse;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionListResponse;
import ru.mdorofeev.finance.scheduler.service.ScheduledService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Disabled
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.finance-service-rest-base=http://localhost:8080",
        "app.integration.repeated-payment-service-config-rest-base=http://localhost:8082/repeatedPayment",
        "app.integration.auth-service-rest-base=http://localhost:8081/auth",
})
public class SchedulerIntegrationTest {

    @Value("${app.integration.repeated-payment-service-config-rest-base}")
    private String repeatedServiceBase;

    @Value("${app.integration.auth-service-rest-base}")
    private String authServiceBase;

    @Value("${app.integration.finance-service-rest-base}")
    private String financeServiceBase;

    @Autowired
    private ScheduledService scheduledService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void allTest() throws ServiceException {
        String login = UUID.randomUUID().toString();

        // clients
        String date = format.format(new Date());
        AuthServiceClient authServiceClient = new AuthServiceClient(authServiceBase);
        FinanceServiceClient financeClient = new FinanceServiceClient(financeServiceBase);

        // create user
        authServiceClient.createUser(login, login);
        Long session = authServiceClient.createSession(login, login);
        Long userId = authServiceClient.findBySession(session);

        // create repeated data
        Long accountId = financeClient.createAccount(session, "account");
        Long categoryId = financeClient.createCategory(session, "category");
        RepeatedServiceClient client = new RepeatedServiceClient(repeatedServiceBase);
        client.addScheduledData(session, categoryId, accountId, 100.0, date);

        // check no transactions
        TransactionListResponse trs = financeClient.getTransactions(date, session);
        Assertions.assertEquals(0, trs.getResult().size());

        // create payments
        scheduledService.executeCreateRepeatedPayment();

        // check transactions exists
        trs = financeClient.getTransactions(date, session);
        Assertions.assertEquals(1, trs.getResult().size());
    }

    @Test
    public void currencyTest() throws ServiceException {
        String login = UUID.randomUUID().toString();

        AuthServiceClient client = new AuthServiceClient(authServiceBase);
        client.createUser(login, login);
        Long session = client.createSession(login, login);
        Long userId = client.findBySession(session);
        System.out.println(session);
        System.out.println(userId);

        FinanceServiceClient financeClient = new FinanceServiceClient(financeServiceBase);
        financeClient.createCurrency( login, 23.0);
        Double cur = financeClient.getCurrency( login);
        Assertions.assertEquals(23.0, cur);

        financeClient.updateCurrency( login, 24.0);
        cur = financeClient.getCurrency( login);
        Assertions.assertEquals(24.0, cur);
    }
}
