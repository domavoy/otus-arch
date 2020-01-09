package ru.mdorofeev.finance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.parser.MoneyProDataImport;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.service.AuthService;
import ru.mdorofeev.finance.service.ExportService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

@ActiveProfiles("h2mem")
@SpringBootTest
public class ExportTest {

    @Autowired
    AuthService authService;

    @Autowired
    MoneyProDataImport moneyProDataImport;

    @Autowired
    ExportService exportService;

    @Test
    public void export() throws IOException, ServiceException, URISyntaxException {
        User user = authService.createUser("user1", "password1");
        moneyProDataImport.dataImport(user, "moneyPro.csv");

        //TODO: File.createTempFile("test.csv", ".csv");
        String location = exportService.export(File.createTempFile("test.csv", ".csv").getAbsolutePath(), user, new Date(LocalDate.parse("2010-01-01").toEpochDay()));
        System.out.println(location);
    }
}
