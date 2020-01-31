package ru.mdorofeev.finance.scheduler.external;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SbrfCurrencyParser {

    public static ValCurs parserFromResources(String fileName) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        InputStream inputStream = SbrfCurrencyParser.class.getClassLoader().getResourceAsStream(fileName);
        return  (ValCurs) jaxbUnmarshaller.unmarshal(inputStream);
    }

    public static ValCurs parserFromUrl(String url) throws JAXBException, MalformedURLException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return  (ValCurs) jaxbUnmarshaller.unmarshal(new URL(url));
    }
}
