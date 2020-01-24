package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Disabled;
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
import ru.mdorofeev.finance.core.service.ExportService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

@Disabled
@ActiveProfiles({"db-postgres"})
@SpringBootTest
public class Runner {

    @Spy
    @InjectMocks
    AuthIntegrationService authProxyService =Mockito.mock(AuthIntegrationService.class);

    @Autowired
    ImportService moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    void importMoneyProFolder() throws IOException, ServiceException {
        Mockito.when(authProxyService.findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.findBySession(100L);
        moneyProDataImport.importMoneyProFolder(userId, "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money");
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
