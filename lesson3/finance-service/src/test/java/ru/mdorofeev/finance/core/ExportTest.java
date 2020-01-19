package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.service.ImportService;
import ru.mdorofeev.finance.core.service.AuthProxyService;
import ru.mdorofeev.finance.core.service.ExportService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest
public class ExportTest {

    @Spy
    @InjectMocks
    AuthProxyService authProxyService = new AuthProxyService();

    @Autowired
    ImportService moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    public void export() throws IOException, ServiceException, URISyntaxException {
        Mockito.when(authProxyService.findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.findBySession(100L);
        moneyProDataImport.importMoneyPro(userId, "moneyPro.csv");

        //TODO: File.createTempFile("test.csv", ".csv");
        String location = exportService.export(File.createTempFile("test.csv", ".csv").getAbsolutePath(), userId, new Date(LocalDate.parse("2010-01-01").toEpochDay()));
        System.out.println(location);
    }
}
