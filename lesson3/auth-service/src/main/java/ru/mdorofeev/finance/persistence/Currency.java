package ru.mdorofeev.finance.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "currency", schema = "public")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String name;
    private Boolean isDefault;
    private BigDecimal rate;

    public enum Values {
        RUB(1),
        USD(2),
        EUR(3);

        public int id;

        Values(int id) {
            this.id = id;
        }
    }
}
