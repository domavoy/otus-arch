package ru.mdorofeev.finance.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.persistence.Account;
import ru.mdorofeev.finance.persistence.Category;
import ru.mdorofeev.finance.persistence.Currency;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.persistence.dict.TransactionType;
import ru.mdorofeev.finance.service.ConfigurationService;
import ru.mdorofeev.finance.service.MainService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

//TODO: P2: REFACTORING - rename package to import
@Service
public class MoneyProDataImport {

    @Autowired
    MainService mainService;

    @Autowired
    MoneyProParser parser;

    @Autowired
    ConfigurationService configurationService;

    public void dataImport(User user, String fileName) throws IOException, ServiceException, URISyntaxException {
        parser.parseFile(fileName, new ParserHandler() {
            @Override
            public void process(MoneyProData data) throws ServiceException {
                importData(data, user);
            }
        }, true);
    }

    public void dataImportFromFolder(User user, String folderName) throws IOException {
        Files.list(Paths.get(folderName))
                .filter(s -> s.toString().endsWith(".csv"))
                .sorted()
                .forEach(file -> {
                    String fileName = file.toAbsolutePath().toString();
                    parser.parseFile(fileName, new ParserHandler() {

                        @Override
                        public void process(MoneyProData data) throws ServiceException {
                            importData(data, user);
                        }
                    });
                });
    }

    private void importData(MoneyProData moneyProData, User user) throws ServiceException {
        if (moneyProData.getType() == TransactionType.NEW_ACCOUNT) {
            Currency currency = configurationService.createOrUpdateCurrency(moneyProData.getCurrency());
            configurationService.createAccount(user, currency, moneyProData.getAccount());
        }

        if (Arrays.asList(TransactionType.MONEY_TRANSFER, TransactionType.INCOME, TransactionType.EXPENSE).
                contains(moneyProData.getType())) {
            Currency currency = configurationService.createOrUpdateCurrency(moneyProData.getCurrency());
            Category category = configurationService.getCategory(user, moneyProData.getType(), moneyProData.getCategory());
            if (TransactionType.MONEY_TRANSFER != moneyProData.getType() && category == null) {
                category = configurationService.createCategory(user, moneyProData.getType(), moneyProData.getCategory());
            }

            Account account = configurationService.getAccount(user, currency, moneyProData.getAccount());
            if (account == null) {
                account = configurationService.createAccount(user, currency, moneyProData.getAccount());
            }

            Account toAccount = null;
            Double toMoney = null;
            if (TransactionType.MONEY_TRANSFER == moneyProData.getType()) {
                toMoney = moneyProData.getToMoney().doubleValue();

                toAccount = configurationService.getAccount(user, currency, moneyProData.getToAccount());
                if (toAccount == null) {
                    toAccount = configurationService.createAccount(user, currency, moneyProData.getToAccount());
                }
            }

            Date date = moneyProData.getDate();

            mainService.createTransactionNative(user, date, moneyProData.getType(),
                    account, toAccount, category, moneyProData.getMoney().doubleValue(), toMoney, moneyProData.getComment());
        }
    }
}
