package ru.mdorofeev.finance.core.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
//TODO: P3: Validate imported rows by balance field from MoneyPro
public class MoneyProParser {

    public void parseFile(String fileName, ParserHandler handler) {
        parseFile(fileName, handler, false);
    }

    public void parseFile(String fileName, ParserHandler handler, boolean isLocalResource) {
        try {
            InputStream inputStream;
            if(isLocalResource){
                inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            } else {
                inputStream = new FileInputStream(fileName);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (reader.ready()) {
                    String line = reader.readLine();
                    if (line.startsWith("Дата;Сумма;")) {
                        continue;
                    }

                    handler.process(parse(line));
                }
            }
        }catch (Exception e){
            throw new MoneyProImportException("failed to parse file: " + fileName, e);
        }
    }

    public MoneyProData parse(String str) throws ParseException {
        String tokens[] = str.split(";");

        MoneyProData moneyProData = new MoneyProData();

        // define transaction type, skip if not supported
        if (tokens[8].equals("Открытие баланса")) {
            moneyProData.setType(TransactionType.NEW_ACCOUNT);
        }
        if (tokens[8].equals("Доход")) {
            moneyProData.setType(TransactionType.INCOME);
        }
        if (tokens[8].equals("Расход")) {
            moneyProData.setType(TransactionType.EXPENSE);
        }
        if (tokens[8].equals("Перевод средств")) {
            moneyProData.setType(TransactionType.MONEY_TRANSFER);
        }

        if (moneyProData.getType() == null) {
            return null;
        }

        // date
        Locale locale = new Locale("ru");
        SimpleDateFormat format = new SimpleDateFormat("dd MMMMM yyyy г., HH:mm:ss", locale);
        moneyProData.setDate(format.parse(tokens[0]));

        // from account name
        moneyProData.setAccount(tokens[2]);

        // to account name
        if ((!"".equals(tokens[4]))) {
            moneyProData.setToAccount(tokens[4]);
        }

        // money and currency
        MoneyInfo money = parseMoney(tokens[1]);
        moneyProData.setMoney(money.getMoney());
        moneyProData.setCurrency(money.getCurrency());

        // to money
        MoneyInfo toMoney = parseMoney(tokens[3]);
        if (toMoney != null) {
            moneyProData.setToMoney(toMoney.getMoney());
        }


        // balance
        MoneyInfo balance = parseMoney(tokens[5]);
        moneyProData.setBalance(balance.getMoney());

        // compare currency for money/balance
        if (!money.getCurrency().equals(balance.getCurrency())) {
            throw new RuntimeException("balance.currency != money.currency: " + money.getCurrency() + ", " + balance.getCurrency());
        }

        // category
        moneyProData.setCategory(tokens[6].trim());

        // comment
        moneyProData.setComment(tokens[7].trim());

        return moneyProData;
    }

    private MoneyInfo parseMoney(String str) {
        MoneyInfo moneyInfo = new MoneyInfo();
        if ("".equals(str)) {
            return null;
        }

        String moneyStr = StringUtils.substringBeforeLast(str, " ").
                replace(" ", "").replace(",", ".");
        moneyInfo.setMoney(BigDecimal.valueOf(Double.valueOf(moneyStr.replace(" ", ""))));


        String currencyStr = StringUtils.substringAfterLast(str, " ");
        moneyInfo.setCurrency(currencyFrom(currencyStr));

        return moneyInfo;
    }

    //TODO: P2: hardcoded currency => fill predefined from dict
    private String currencyFrom(String moneyProValue) {
        if (moneyProValue.equals("₽")) {
            return "RUB";
        }

        if (moneyProValue.equals("US$")) {
            return "USD";
        }

        if (moneyProValue.equals("€")) {
            return "EUR";
        }

        throw new RuntimeException("Illegal money pro currency: " + moneyProValue);
    }
}
