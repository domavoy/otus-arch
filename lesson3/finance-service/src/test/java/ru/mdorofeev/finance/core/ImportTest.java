package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.service.ImportService;
import ru.mdorofeev.finance.core.integration.AuthIntegrationService;
import ru.mdorofeev.finance.core.service.ConfigurationService;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest
public class ImportTest {

    @Spy
    @InjectMocks
    AuthIntegrationService authProxyService = Mockito.mock(AuthIntegrationService.class);

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    ImportService moneyProDataImport;

    @Test
    void moneyProImport() throws IOException, ServiceException, URISyntaxException {
        Mockito.when(authProxyService.findBySession(anyLong())).thenReturn(101L);

        Long userId = authProxyService.findBySession(101L);
        moneyProDataImport.importMoneyPro(userId, "moneyPro.csv");

        Assertions.assertEquals(0.0, configurationService.getAccountByName(userId, "Сберегательный").getAmount(), "cur account");
        Assertions.assertEquals(0.0, configurationService.getAccountByName(userId, "Доллары citi").getAmount(), "cur account");
        Assertions.assertEquals(7660.0, configurationService.getAccountByName(userId, "Наличка").getAmount(), "cur account");
        Assertions.assertEquals(20910.0, configurationService.getAccountByName(userId, "Кредитка").getAmount(), "cur account");

    }
}