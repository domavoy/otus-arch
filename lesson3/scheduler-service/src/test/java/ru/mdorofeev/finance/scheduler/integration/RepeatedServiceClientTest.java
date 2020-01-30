package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.mdorofeev.finance.common.exception.ServiceException;

@Disabled
@ActiveProfiles({"db-h2mem"})
@SpringBootTest
@TestPropertySource(properties = {
        "app.integration.repeated-payment-service-config-rest-base=http://localhost:8082/repeatedPayment",
})
public class RepeatedServiceClientTest {

    @Value("${app.integration.repeated-payment-service-config-rest-base}")
    private String repeatedServiceBase;

    @Test
    public void test() throws ServiceException {
        RepeatedServiceClient client = new RepeatedServiceClient(repeatedServiceBase);
        client.addScheduledData(100L, "category", 100.0);
        String data = client.getScheduledData(100L);
    }
}
