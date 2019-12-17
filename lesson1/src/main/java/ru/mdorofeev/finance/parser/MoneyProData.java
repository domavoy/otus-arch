package ru.mdorofeev.finance.parser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.persistence.dict.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MoneyProData {
    TransactionType type;

    Date date;
    String account;
    String category;
    String currency;

    BigDecimal money;
    BigDecimal balance;

    String toAccount;
    BigDecimal toMoney;

    String comment;
}
