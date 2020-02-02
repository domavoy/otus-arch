package ru.mdorofeev.finance.scheduler.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.external.SbrfCurrencyParser;
import ru.mdorofeev.finance.scheduler.external.ValCurs;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CurrencyProvider {

    @Value("${app.external.sbrf-get-currency-rates}")
    String getCurrencyBasicUrl;

    public ValCurs getForDate(Date date) throws ServiceException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String dateAppend = format.format(date);
            return SbrfCurrencyParser.parserFromUrl(new URL(getCurrencyBasicUrl + dateAppend));
        } catch (JAXBException | MalformedURLException e) {
            throw new ServiceException(e.getMessage(), "FAILED_TO_LOAD_CURRENCY", e);
        }
    }
}
