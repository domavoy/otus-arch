package ru.mdorofeev.finance.core.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.service.ConfigurationService;
import ru.mdorofeev.finance.core.service.MainService;

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

    public void dataImport(Long userId, String fileName) throws IOException, ServiceException, URISyntaxException {
        parser.parseFile(fileName, new ParserHandler() {
            @Override
            public void process(MoneyProData data) throws ServiceException {
                importData(data, userId);
            }
        }, true);
    }

    public void dataImportFromFolder(Long userId, String folderName) throws IOException {
        Files.list(Paths.get(folderName))
                .filter(s -> s.toString().endsWith(".csv"))
                .sorted()
                .forEach(file -> {
                    String fileName = file.toAbsolutePath().toString();
                    parser.parseFile(fileName, new ParserHandler() {

                        @Override
                        public void process(MoneyProData data) throws ServiceException {
                            importData(data, userId);
                        }
                    });
                });
    }

    private void importData(MoneyProData moneyProData, Long userId) throws ServiceException {
        if (moneyProData.getType() == TransactionType.NEW_ACCOUNT) {
            Currency currency = configurationService.createOrUpdateCurrency(moneyProData.getCurrency());
            configurationService.createAccount(userId, currency, moneyProData.getAccount());
        }

        if (Arrays.asList(TransactionType.MONEY_TRANSFER, TransactionType.INCOME, TransactionType.EXPENSE).
                contains(moneyProData.getType())) {
            Currency currency = configurationService.createOrUpdateCurrency(moneyProData.getCurrency());
            Category category = configurationService.getCategory(userId, moneyProData.getType(), moneyProData.getCategory());
            if (TransactionType.MONEY_TRANSFER != moneyProData.getType() && category == null) {
                category = configurationService.createCategory(userId, moneyProData.getType(), moneyProData.getCategory());
            }

            Account account = configurationService.getAccount(userId, currency, moneyProData.getAccount());
            if (account == null) {
                account = configurationService.createAccount(userId, currency, moneyProData.getAccount());
            }

            Account toAccount = null;
            Double toMoney = null;
            if (TransactionType.MONEY_TRANSFER == moneyProData.getType()) {
                toMoney = moneyProData.getToMoney().doubleValue();

                toAccount = configurationService.getAccount(userId, currency, moneyProData.getToAccount());
                if (toAccount == null) {
                    toAccount = configurationService.createAccount(userId, currency, moneyProData.getToAccount());
                }
            }

            Date date = moneyProData.getDate();

            mainService.createTransactionNative(userId, date, moneyProData.getType(),
                    account, toAccount, category, moneyProData.getMoney().doubleValue(), toMoney, moneyProData.getComment());
        }
    }
}
