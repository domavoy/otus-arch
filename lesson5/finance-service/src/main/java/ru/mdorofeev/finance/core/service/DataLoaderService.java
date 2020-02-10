package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.exception.ServiceException;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class DataLoaderService {

    @Autowired
    ImportService moneyProDataImport;

    public void loadData(Long userId, String url) throws IOException, ServiceException, URISyntaxException {
        moneyProDataImport.importMoneyPro(userId, url);

    }
}
