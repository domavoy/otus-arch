package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.User;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.repository.AccountRepository;
import ru.mdorofeev.finance.core.repository.CategoryRepository;
import ru.mdorofeev.finance.core.repository.CurrencyRepository;

import java.util.List;

@Service
public class ConfigurationService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Currency createOrUpdateCurrency(String name) throws ServiceException {
        Currency cur = currencyRepository.findByName(name);
        if (cur == null) {
            throw new ServiceException("CURRENCY_NOT_FOUND");
        }

        return cur;
    }

    public Category createCategory(User user, TransactionType type, String name) throws ServiceException {
        Category acc = categoryRepository.findByUserAndTransactionTypeAndName(user, type.id, name);
        if (acc != null) {
            throw new ServiceException("CATEGORY_ALREADY_EXISTS");
        }
        Category category = new Category();
        category.setUser(user);
        category.setTransactionType(type.id);
        category.setName(name);

        return categoryRepository.save(category);
    }

    public Category getCategory(User user, TransactionType type, String name) {
        return categoryRepository.findByUserAndTransactionTypeAndName(user, type.id, name);
    }

    public Category getCategory(User user, String name) {
        return categoryRepository.findByUserAndName(user, name);
    }

    public Account createAccount(User user, Currency currency, String name) throws ServiceException {
        Account acc = accountRepository.findByUserAndCurrencyAndName(user, currency, name);
        if (acc != null) {
            throw new ServiceException("ACCOUNT_ALREADY_EXISTS");
        }

        Account account = new Account();
        account.setUser(user);
        account.setCurrency(currency);
        account.setName(name);
        account.setAmount(0.0);

        return accountRepository.save(account);
    }

    public Account getAccount(User user, Currency currency, String name) {
        return accountRepository.findByUserAndCurrencyAndName(user, currency, name);
    }

    public Account getAccountByName(User user, String name) {
        return accountRepository.findByUserAndName(user, name);
    }

    public List<Category> getCategories(Long sessionId) {
        return categoryRepository.getBySession(sessionId);
    }

}
