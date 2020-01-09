package ru.mdorofeev.finance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.User;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.service.AuthService;
import ru.mdorofeev.finance.core.service.ConfigurationService;
import ru.mdorofeev.finance.core.service.MainService;

import java.io.IOException;
import java.util.Date;

@ActiveProfiles("h2mem")
@SpringBootTest
class RepositoryTest {

    @Autowired
    MainService mainService;

    @Autowired
    AuthService authService;

    @Autowired
    ConfigurationService configurationService;

    @Test
    void transactionLogic() throws IOException, ServiceException {
        User user = authService.createUser("1", "1");
        Currency currency = configurationService.createOrUpdateCurrency("RUB");

        Account account = configurationService.createAccount(user, currency, "наличка");
        Account toAccount = configurationService.createAccount(user, currency, "кредитка");
        Category categoryIncome = configurationService.createCategory(user, TransactionType.INCOME, "income");
        Category categoryExpense = configurationService.createCategory(user, TransactionType.EXPENSE, "expense");

        // test one account
        transaction(user, account, categoryIncome, 200.0);
        transaction(user, account, categoryExpense, 50.0);
        Assertions.assertEquals(150.0, configurationService.getAccountByName(user, "наличка").getAmount(), "add amount");

        // test money transfer
        transaction(user, toAccount, categoryIncome, 400.0);
        mainService.moneyTransfer(user, new Date(), account, toAccount, 60.0, "");
        Assertions.assertEquals(90.0, configurationService.getAccountByName(user, "наличка").getAmount(), "cur account");
        Assertions.assertEquals(460.0, configurationService.getAccountByName(user, "кредитка").getAmount(), "new account");
    }

    private void transaction(User user, Account account, Category categoryIncome, Double money) throws ServiceException {
        mainService.createTransaction(user, new Date(), account, categoryIncome, money, "text");
    }
}
