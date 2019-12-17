package ru.mdorofeev.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.parser.MoneyProDataImport;
import ru.mdorofeev.finance.persistence.User;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class PredefinedDataLoader {

    @Autowired
    AuthService authService;

    @Autowired
    MoneyProDataImport moneyProDataImport;

    public void loadData(String login, String password, String url) throws IOException, ServiceException, URISyntaxException {
        User user = authService.createUser(login, password);
        moneyProDataImport.dataImport(user, url);

    }
}
