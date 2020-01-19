package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.parser.MoneyProDataImport;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class PredefinedDataLoader {

    @Autowired
    MoneyProDataImport moneyProDataImport;

    public void loadData(Long userId, String url) throws IOException, ServiceException, URISyntaxException {
        moneyProDataImport.dataImport(userId, url);

    }
}
