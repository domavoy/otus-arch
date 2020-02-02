package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.scheduler.external.SbrfCurrencyParser;
import ru.mdorofeev.finance.scheduler.external.ValCurs;
import ru.mdorofeev.finance.scheduler.service.CurrencyProvider;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.net.URL;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoadCurrencyIntegrationTest {

    @Autowired
    CurrencyProvider currencyProvider;

    @Test
    public void test() throws ServiceException {
        ValCurs valCurs = currencyProvider.getForDate(DateUtil.date("2020-01-01"));
        valCurs.getValutes().stream().forEach(val -> {
            if(val.getNumCode().equals("208")){
                Assertions.assertEquals("DKK", val.getCharCode());
                Assertions.assertEquals("10", val.getNominal());
                Assertions.assertEquals("Датских крон", val.getName());
                Assertions.assertEquals(92.8776, val.getValue());
            }
        });
    }
}
