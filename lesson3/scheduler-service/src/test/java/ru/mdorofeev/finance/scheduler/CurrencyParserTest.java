package ru.mdorofeev.finance.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mdorofeev.finance.scheduler.external.SbrfCurrencyParser;
import ru.mdorofeev.finance.scheduler.external.ValCurs;
import ru.mdorofeev.finance.scheduler.external.Valute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

public class CurrencyParserTest {

    @Test
    public void parserTest() throws JAXBException {
        ValCurs valCurs = SbrfCurrencyParser.parserFromResources("sbrf-currency.xml");
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
