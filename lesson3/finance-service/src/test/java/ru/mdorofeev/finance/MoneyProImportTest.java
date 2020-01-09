package ru.mdorofeev.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.parser.MoneyProDataImport;
import ru.mdorofeev.finance.core.persistence.User;
import ru.mdorofeev.finance.core.service.AuthService;
import ru.mdorofeev.finance.core.service.ConfigurationService;

import java.io.IOException;
import java.net.URISyntaxException;

@ActiveProfiles("h2mem")
@SpringBootTest
public class MoneyProImportTest {

    @Autowired
    AuthService authService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    MoneyProDataImport moneyProDataImport;

    @Test
    void dataImport() throws IOException, ServiceException, URISyntaxException {
        User user = authService.createUser("user", "password");
        moneyProDataImport.dataImport(user, "moneyPro.csv");

        Assertions.assertEquals(0.0, configurationService.getAccountByName(user, "Сберегательный").getAmount(), "cur account");
        Assertions.assertEquals(0.0, configurationService.getAccountByName(user, "Доллары citi").getAmount(), "cur account");
        Assertions.assertEquals(7660.0, configurationService.getAccountByName(user, "Наличка").getAmount(), "cur account");
        Assertions.assertEquals(20910.0, configurationService.getAccountByName(user, "Кредитка").getAmount(), "cur account");

    }
}