package ru.mdorofeev.finance.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.api.common.Processor;
import ru.mdorofeev.finance.api.model.common.Response;
import ru.mdorofeev.finance.api.model.response.AccountListResponse;
import ru.mdorofeev.finance.api.model.response.AccountResponse;
import ru.mdorofeev.finance.api.model.response.StringListResponse;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.persistence.Account;
import ru.mdorofeev.finance.persistence.Category;
import ru.mdorofeev.finance.persistence.Currency;
import ru.mdorofeev.finance.persistence.dict.TransactionType;
import ru.mdorofeev.finance.service.AuthService;
import ru.mdorofeev.finance.service.ConfigurationService;
import ru.mdorofeev.finance.service.MainService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConfigurationControllerImpl implements ConfigurationController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationControllerImpl.class);
    @Autowired
    ConfigurationService configurationService;
    @Autowired
    private MainService mainService;
    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<StringListResponse> getCategories(Long sessionId) {
        return Processor.wrapExceptionsAndAuth(authService, sessionId, user -> {
            List<Category> data = configurationService.getCategories(sessionId);
            List<String> names = data.stream().map(a -> a.getName()).collect(Collectors.toList());
            return new ResponseEntity<>(new StringListResponse(null, names), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> addCategory(Long sessionId, String categoryType, String name) {
        return Processor.wrapExceptionsAndAuth(authService, sessionId, user -> {
            if (!Arrays.asList(TransactionType.EXPENSE.name(), TransactionType.INCOME.name()).
                    contains(categoryType)) {
                throw new ServiceException("Incorrect categoryType: should be: " +
                        TransactionType.EXPENSE.name() + "/" + TransactionType.INCOME.name());
            }

            configurationService.createCategory(user, TransactionType.from(categoryType), name);
            List<Category> data = configurationService.getCategories(sessionId);
            List<String> names = data.stream().map(a -> a.getName()).collect(Collectors.toList());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<AccountListResponse> getAccounts(Long sessionId) {
        return Processor.wrapExceptionsAndAuth(authService, sessionId, user -> {
            List<Account> data = mainService.getAccounts(sessionId);
            List<AccountResponse> names = data.stream().map(temp -> {
                AccountResponse response = new AccountResponse();
                response.setName(temp.getName());
                response.setCurrency(temp.getCurrency().getName());
                return response;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(new AccountListResponse(names), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> addAccount(Long sessionId, String currency, String name) {
        return Processor.wrapExceptionsAndAuth(authService, sessionId, user -> {
            Currency cur = configurationService.createOrUpdateCurrency(currency);
            configurationService.createAccount(user, cur, name);
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }
}