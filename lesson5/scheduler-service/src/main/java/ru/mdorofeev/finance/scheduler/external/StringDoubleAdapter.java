package ru.mdorofeev.finance.scheduler.external;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StringDoubleAdapter extends XmlAdapter<String, Double> {
    private final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public Double unmarshal(String xml) throws Exception {
        return Double.valueOf(xml.replaceAll(",", "."));
    }

    @Override
    public String marshal(Double object) throws Exception {
        return object.toString();
    }

}
