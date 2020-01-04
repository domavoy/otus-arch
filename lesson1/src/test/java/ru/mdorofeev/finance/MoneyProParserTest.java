package ru.mdorofeev.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.mdorofeev.finance.parser.MoneyProData;
import ru.mdorofeev.finance.parser.MoneyProParser;
import ru.mdorofeev.finance.parser.ParserHandler;
import ru.mdorofeev.finance.persistence.dict.TransactionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;

public class MoneyProParserTest {

    @Test
    public void importTest() throws IOException, URISyntaxException {
        MoneyProParser parser = new MoneyProParser();

        parser.parseFile("moneyPro.csv", new ParserHandler() {
            @Override
            public void process(MoneyProData data) {
                Assertions.assertNotNull(data);
            }
        }, true);
    }

    @Test
    public void parseTest() throws ParseException {

        MoneyProParser parser = new MoneyProParser();

        MoneyProData data = parser.parse("13 июля 2011 г., 11:59:59;10,05 ₽;Сберегательный;;;0,00 ₽;; ;Открытие баланса;;;;");
        Assertions.assertEquals("Сберегательный", data.getAccount(), "newBalance => account");
        Assertions.assertEquals("", data.getComment(), "newBalance => comment");
        Assertions.assertEquals("RUB", data.getCurrency(), "newBalance => currency");
        Assertions.assertEquals(null, data.getToAccount(), "newBalance => toAccount");
        Assertions.assertEquals(BigDecimal.valueOf(10.05), data.getMoney(), "newBalance => money");
        Assertions.assertEquals(BigDecimal.valueOf(0.0), data.getBalance(), "newBalance => balance");
        Assertions.assertEquals(TransactionType.NEW_ACCOUNT, data.getType(), "newBalance => type");

        data = parser.parse("13 июля 2011 г., 12:00:00;3 500,00 ₽;Наличка;;;3 500,00 ₽;Другое+ ; В арзамас 22 числа;Доход;;;;");
        Assertions.assertEquals("Наличка", data.getAccount(), "income => account");
        Assertions.assertEquals("В арзамас 22 числа", data.getComment(), "income => comment");
        Assertions.assertEquals("RUB", data.getCurrency(), "income => currency");
        Assertions.assertEquals(null, data.getToAccount(), "income => toAccount");
        Assertions.assertEquals(BigDecimal.valueOf(3500.00), data.getMoney(), "income => money");
        Assertions.assertEquals(BigDecimal.valueOf(3500.00), data.getBalance(), "income => balance");
        Assertions.assertEquals(TransactionType.INCOME, data.getType(), "income => type");
        Assertions.assertEquals("Другое+", data.getCategory(), "expense => category");

        data = parser.parse("17 июля 2011 г., 12:00:00;1 095,00 ₽;Кредитка;;;17 773,00 ₽;Путешествия : Путешествия  ; В арзамас 22 числа;Расход;;;;");
        Assertions.assertEquals("Кредитка", data.getAccount(), "expense => account");
        Assertions.assertEquals("В арзамас 22 числа", data.getComment(), "expense => comment");
        Assertions.assertEquals("RUB", data.getCurrency(), "expense => currency");
        Assertions.assertEquals(null, data.getToAccount(), "expense => toAccount");
        Assertions.assertEquals(BigDecimal.valueOf(1095.0), data.getMoney(), "expense => money");
        Assertions.assertEquals(BigDecimal.valueOf(17773.0), data.getBalance(), "expense => balance");
        Assertions.assertEquals(TransactionType.EXPENSE, data.getType(), "expense => type");
        Assertions.assertEquals("Путешествия : Путешествия", data.getCategory(), "expense => category");

        data = parser.parse("8 сент. 2011 г., 12:00:00;1 900,00 ₽;Кредитка;1 900,00 ₽;Наличка;71 255,00 ₽;; ;Перевод средств;;;;");
        Assertions.assertEquals("Кредитка", data.getAccount(), "transfer => account");
        Assertions.assertEquals("", data.getComment(), "transfer => comment");
        Assertions.assertEquals("RUB", data.getCurrency(), "transfer => currency");
        Assertions.assertEquals("Наличка", data.getToAccount(), "transfer => toAccount");
        Assertions.assertEquals(BigDecimal.valueOf(1900.0), data.getMoney(), "transfer => money");
        Assertions.assertEquals(BigDecimal.valueOf(1900.0), data.getToMoney(), "transfer => money");
        Assertions.assertEquals(BigDecimal.valueOf(71255.0), data.getBalance(), "transfer => balance");
        Assertions.assertEquals(TransactionType.MONEY_TRANSFER, data.getType(), "transfer => type");
        Assertions.assertEquals("", data.getCategory(), "transfer => category");
    }
}
