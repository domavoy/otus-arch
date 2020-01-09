package ru.mdorofeev.finance.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MoneyInfo {
    BigDecimal money;
    String currency;
}
