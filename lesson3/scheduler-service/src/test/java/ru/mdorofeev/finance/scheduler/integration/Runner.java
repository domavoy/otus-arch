package ru.mdorofeev.finance.scheduler.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.mdorofeev.finance.scheduler.external.SbrfCurrencyParser;
import ru.mdorofeev.finance.scheduler.external.ValCurs;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;

@Disabled
public class Runner {

    @Test
    public void test() throws JAXBException, MalformedURLException {
        ValCurs valCurs = SbrfCurrencyParser.parserFromUrl("http://www.cbr.ru/scripts/XML_daily.asp?date_req=01/01/2020");
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
