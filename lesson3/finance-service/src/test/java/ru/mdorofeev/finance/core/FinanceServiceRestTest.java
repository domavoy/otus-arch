package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.api.model.response.CurrencyResponse;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.core.api.ConfigurationController;
import ru.mdorofeev.finance.core.api.MainController;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceServiceRestTest {

    @Autowired
    private ConfigurationController configurationController;

    @MockBean
    AuthServiceClient authClient;

    @Test
    public void currencyTest() throws ServiceException {
        Mockito.when(authClient.findBySession(100L)).thenReturn(100L);

        configurationController.createCurrency(100L, "LAL", 23.0);
        ResponseEntity<CurrencyResponse> cur = configurationController.getCurrency(100L, "LAL");
        Assertions.assertEquals(23.0, cur.getBody().getResult());

        configurationController.updateCurrency(100L, "LAL", 24.0);
        cur = configurationController.getCurrency(100L, "LAL");
        Assertions.assertEquals(24.0, cur.getBody().getResult());
    }
}
