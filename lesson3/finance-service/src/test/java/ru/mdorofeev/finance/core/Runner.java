package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.core.service.ImportService;
import ru.mdorofeev.finance.core.integration.AuthIntegrationService;
import ru.mdorofeev.finance.core.service.ExportService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyLong;

@Disabled
@ActiveProfiles({"db-postgres"})
@SpringBootTest
public class Runner {

    @Spy
    @InjectMocks
    AuthIntegrationService authProxyService =Mockito.mock(AuthIntegrationService.class);

    @Spy
    @InjectMocks
    AuthServiceClient authClient = Mockito.mock(AuthServiceClient.class);

    @Autowired
    ImportService moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    void importMoneyProFolder() throws IOException, ServiceException {
        Mockito.when(authProxyService.client()).thenReturn(authClient);
        Mockito.when(authClient.findBySession(anyLong())).thenReturn(100L);

        Long userId = authProxyService.client().findBySession(100L);
        moneyProDataImport.importMoneyProFolder(userId, "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money");
    }

    @Test
    public void exportToFile() throws IOException, ServiceException, URISyntaxException {
        Mockito.when(authProxyService.client().findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.client().findBySession(100L);

        exportService.export(
                "/Users/domavoy/.yandex.disk/21738021/Yandex.Disk.localized/Dropbox/backup/money.csv",
                userId, new Date(LocalDate.parse("2010-01-01").toEpochDay()));
    }
}
