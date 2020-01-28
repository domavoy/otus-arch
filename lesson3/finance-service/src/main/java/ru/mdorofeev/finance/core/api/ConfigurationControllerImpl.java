package ru.mdorofeev.finance.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.auth.client.ProcessWithUserWrapper;
import ru.mdorofeev.finance.core.api.model.common.Response;
import ru.mdorofeev.finance.core.api.model.response.AccountListResponse;
import ru.mdorofeev.finance.core.api.model.response.AccountResponse;
import ru.mdorofeev.finance.core.api.model.response.StringListResponse;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.service.ConfigurationService;
import ru.mdorofeev.finance.core.service.TransactionService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConfigurationControllerImpl implements ConfigurationController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationControllerImpl.class);

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private TransactionService mainService;

    @Value("${app.integration.auth-service-rest.base}")
    private String authServiceBase;

    @Autowired
    private AuthServiceClient authClient;

    @Bean
    public AuthServiceClient authClient() {
        return new AuthServiceClient(authServiceBase);
    }

    @Override
    public ResponseEntity<StringListResponse> getCategories(Long sessionId) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authClient, sessionId, user -> {
            List<Category> data = configurationService.getCategories(sessionId);
            List<String> names = data.stream().map(a -> a.getName()).collect(Collectors.toList());
            return new ResponseEntity<>(new StringListResponse(null, names), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> addCategory(Long sessionId, String categoryType, String name) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authClient, sessionId, user -> {
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
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authClient, sessionId, user -> {
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
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authClient, sessionId, user -> {
            Currency cur = configurationService.createOrUpdateCurrency(currency);
            configurationService.createAccount(user, cur, name);
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }
}