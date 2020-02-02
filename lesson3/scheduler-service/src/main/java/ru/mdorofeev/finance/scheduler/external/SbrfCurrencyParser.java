package ru.mdorofeev.finance.scheduler.external;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SbrfCurrencyParser {

    public static ValCurs parserFromUrl(URL url) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (ValCurs) jaxbUnmarshaller.unmarshal(url);
    }
}
