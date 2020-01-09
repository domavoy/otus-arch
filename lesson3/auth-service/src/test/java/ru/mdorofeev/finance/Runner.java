package ru.mdorofeev.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.parser.MoneyProDataImport;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.service.AuthService;
import ru.mdorofeev.finance.service.ExportService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

@Disabled
@ActiveProfiles("postgres")
@SpringBootTest
public class Runner {

    @Autowired
    AuthService authService;

    @Autowired
    MoneyProDataImport moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    void importMoneyProFolder() throws IOException, ServiceException {
        User user = authService.findUser("user", "password");
        moneyProDataImport.dataImportFromFolder(user, "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money");
    }

    @Test
    public void exportToFile() throws IOException, ServiceException, URISyntaxException {
        User user = authService.findUser("user", "password");
        exportService.export(
                "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money.csv",
                user, new Date(LocalDate.parse("2010-01-01").toEpochDay()));
    }
}
