package ru.mdorofeev.finance.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.service.AuthProxyService;
import ru.mdorofeev.finance.core.service.ConfigurationService;
import ru.mdorofeev.finance.core.service.MainService;

import java.io.IOException;
import java.util.Date;

@ActiveProfiles({"h2mem"})
@SpringBootTest
class RepositoryTest {

    @Autowired
    MainService mainService;

    @Spy
    @InjectMocks
    AuthProxyService authProxyService = new AuthProxyService();

    @Autowired
    ConfigurationService configurationService;

    @Test
    void transactionLogic() throws ServiceException {
        Mockito.when(authProxyService.findBySession(100L)).thenReturn(100L);

        Long userId = authProxyService.findBySession(100l);
        Currency currency = configurationService.createOrUpdateCurrency("RUB");

        Account account = configurationService.createAccount(userId, currency, "наличка");
        Account toAccount = configurationService.createAccount(userId, currency, "кредитка");
        Category categoryIncome = configurationService.createCategory(userId, TransactionType.INCOME, "income");
        Category categoryExpense = configurationService.createCategory(userId, TransactionType.EXPENSE, "expense");

        // test one account
        transaction(userId, account, categoryIncome, 200.0);
        transaction(userId, account, categoryExpense, 50.0);
        Assertions.assertEquals(150.0, configurationService.getAccountByName(userId, "наличка").getAmount(), "add amount");

        // test money transfer
        transaction(userId, toAccount, categoryIncome, 400.0);
        mainService.moneyTransfer(userId, new Date(), account, toAccount, 60.0, "");
        Assertions.assertEquals(90.0, configurationService.getAccountByName(userId, "наличка").getAmount(), "cur account");
        Assertions.assertEquals(460.0, configurationService.getAccountByName(userId, "кредитка").getAmount(), "new account");
    }

    private void transaction(Long userId, Account account, Category categoryIncome, Double money) throws ServiceException {
        mainService.createTransaction(userId, new Date(), account, categoryIncome, money, "text");
    }
}
