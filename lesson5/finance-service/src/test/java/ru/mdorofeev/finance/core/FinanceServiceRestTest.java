package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.common.api.model.response.CurrencyResponse;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.core.api.ConfigurationController;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest
public class FinanceServiceRestTest {

    @Autowired
    private ConfigurationController configurationController;

    @Test
    public void currencyTest() throws ServiceException {

        configurationController.createOrUpdateCurrency( "LAL", 23.0);
        ResponseEntity<CurrencyResponse> cur = configurationController.getCurrency("LAL");
        Assertions.assertEquals(23.0, cur.getBody().getResult());

        configurationController.createOrUpdateCurrency("LAL", 24.0);
        cur = configurationController.getCurrency("LAL");
        Assertions.assertEquals(24.0, cur.getBody().getResult());
    }
}
