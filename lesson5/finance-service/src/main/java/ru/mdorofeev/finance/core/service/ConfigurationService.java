package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.repository.AccountRepository;
import ru.mdorofeev.finance.core.repository.CategoryRepository;
import ru.mdorofeev.finance.core.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    //TODO: P2: support date for currency
    public Currency getCurrency(String name) throws ServiceException {
        Currency cur = currencyRepository.findByName(name);
        if (cur == null) {
            throw new ServiceException("CURRENCY_NOT_FOUND");
        }

        return cur;
    }

    //TODO: P2: support date for currency
    @Transactional
    public Currency createOrUpdateCurrency(String name, Double rate) throws ServiceException {
        Currency cur = currencyRepository.findByName(name);
        if (cur != null) {
            cur.setRate(BigDecimal.valueOf(rate));
            currencyRepository.save(cur);
            return cur;
        }

        Currency currency = new Currency();
        currency.setName(name);
        currency.setRate(BigDecimal.valueOf(rate));
        currency.setIsDefault(false);

        currencyRepository.save(currency);
        return currency;
    }

    public Category createCategory(Long userId, TransactionType type, String name) throws ServiceException {
        Category acc = categoryRepository.findByUserIdAndTransactionTypeAndName(userId, type.id, name);
        if (acc != null) {
            throw new ServiceException("CATEGORY_ALREADY_EXISTS");
        }
        Category category = new Category();
        category.setUserId(userId);
        category.setTransactionType(type.id);
        category.setName(name);

        return categoryRepository.save(category);
    }

    public Category getCategory(Long userId, TransactionType type, String name) {
        return categoryRepository.findByUserIdAndTransactionTypeAndName(userId, type.id, name);
    }

    public Category getCategory(Long userId, String name) {
        return categoryRepository.findByUserIdAndName(userId, name);
    }

    public Account createAccount(Long userId, Currency currency, String name) throws ServiceException {
        Account acc = accountRepository.findByUserIdAndCurrencyAndName(userId, currency, name);
        if (acc != null) {
            throw new ServiceException("ACCOUNT_ALREADY_EXISTS");
        }

        Account account = new Account();
        account.setUserId(userId);
        account.setCurrency(currency);
        account.setName(name);
        account.setAmount(0.0);

        return accountRepository.save(account);
    }

    public Account getAccount(Long userId, Currency currency, String name) {
        return accountRepository.findByUserIdAndCurrencyAndName(userId, currency, name);
    }

    public Account getAccountByName(Long userId, String name) {
        return accountRepository.findByUserIdAndName(userId, name);
    }

    public Account getAccountById(Long accountId) throws ServiceException {
        Optional<Account> data = accountRepository.findById(accountId);
        if(!data.isPresent()){
            throw new ServiceException("ACCOUNT_ID_NOT_FOUND");
        }

        return data.get();
    }

    public Category getCategoryById(Long categoryId) throws ServiceException {
        Optional<Category> data = categoryRepository.findById(categoryId);
        if(!data.isPresent()){
            throw new ServiceException("CATEGORY_ID_NOT_FOUND");
        }

        return data.get();
    }

    public List<Category> getCategories(Long sessionId) {
        return categoryRepository.findByUserId(sessionId);
    }

}
