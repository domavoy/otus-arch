package ru.mdorofeev.finance.scheduler.external;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ValCurs")
public class ValCurs {

    @XmlElement(name = "Valute")
    private List<Valute> valutes = new ArrayList<>();

    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }
}
