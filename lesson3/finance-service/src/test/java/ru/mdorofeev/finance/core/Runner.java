package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.parser.MoneyProDataImport;
import ru.mdorofeev.finance.core.service.AuthProxyService;
import ru.mdorofeev.finance.core.service.ExportService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

@Disabled
//TODO: P1: rewname profiles: db-postgres, test-mock-auth
//TODO: P1: unit tests for import
@ActiveProfiles({"postgres"})
@SpringBootTest
public class Runner {

    @Spy
    @InjectMocks
    AuthProxyService authProxyService = new AuthProxyService();

    @Autowired
    MoneyProDataImport moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    void importMoneyProFolder() throws IOException, ServiceException {
        Mockito.when(authProxyService.findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.findBySession(100L);
        moneyProDataImport.dataImportFromFolder(userId, "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money");
    }

    @Test
    public void exportToFile() throws IOException, ServiceException, URISyntaxException {
        Mockito.when(authProxyService.findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.findBySession(100L);

        exportService.export(
                "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money.csv",
                userId, new Date(LocalDate.parse("2010-01-01").toEpochDay()));
    }
}
