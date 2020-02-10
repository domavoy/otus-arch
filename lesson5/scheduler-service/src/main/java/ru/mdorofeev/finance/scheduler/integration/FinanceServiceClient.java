package ru.mdorofeev.finance.scheduler.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.common.api.model.response.CurrencyResponse;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionListResponse;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionRequestInternal;

@Service
public class FinanceServiceClient {

    @Value("${app.integration.finance-service-rest-base}")
    private String financeServiceBase;

    private RestTemplate restTemplate;

    public static final String financeAddTransactionUri = "/main/addInternalTransaction";
    public static final String financeGetTransactionListUri = "/main/getTransactions?fromDate={fromDate}&sessionId={sessionId}";

    public static final String financeAddCategory = "/config/addCategory?categoryType={categoryType}&name={categoryName}&sessionId={sessionId}";
    public static final String financeAddAccount = "/config/addAccount?currency={currency}&name={categoryName}&sessionId={sessionid}";

    public static final String createCurrency = "/config/createOrUpdateCurrency?currency={currency}&rate={rate}";
    public static final String getCurrency = "/config/getCurrency?currency={currency}";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public FinanceServiceClient(@Value("financeServiceBase") String financeServiceBase) {
        this.financeServiceBase = financeServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public Long createAccount(Long sessionId, String accountName) throws ServiceException {
        LongResponse result = restTemplate.postForEntity(financeServiceBase + financeAddAccount, null,
                LongResponse.class, "RUB", accountName, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getMessage(), result.getError().getCode());
        }

        return result.getResult();
    }

    public Long createCategory(Long sessionId, String category) throws ServiceException {
        LongResponse result = restTemplate.postForEntity(financeServiceBase + financeAddCategory, null,
                LongResponse.class, "INCOME", category, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getMessage(), result.getError().getCode());
        }

        return result.getResult();
    }

    public void createInternalTransaction(Long userId, Long categoryId, Long accountId, Double money, String comment) throws ServiceException {
        TransactionRequestInternal transactionRequest = new TransactionRequestInternal();
        transactionRequest.setUserId(userId);
        transactionRequest.setAccountId(accountId);
        transactionRequest.setCategoryId(categoryId);
        transactionRequest.setComment(comment);
        transactionRequest.setMoney(money);
        System.out.println(transactionRequest);

        Response result = restTemplate.postForEntity(financeServiceBase + financeAddTransactionUri,
                transactionRequest, Response.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
    }

    public TransactionListResponse getTransactions(String forDate, Long sessionId) throws ServiceException {
        TransactionListResponse result = restTemplate.getForEntity(financeServiceBase + financeGetTransactionListUri,
                TransactionListResponse.class, forDate, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }

        return result;
    }

    public void createOrUpdateCurrency(String currencyName, Double rate) throws ServiceException {
        Response result = restTemplate.getForEntity(financeServiceBase + createCurrency,
                Response.class, currencyName, rate).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
    }

    public Double getCurrency(String currencyName) throws ServiceException {
        CurrencyResponse result = restTemplate.getForEntity(financeServiceBase + getCurrency,
                CurrencyResponse.class, currencyName).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }

        return  result.getResult();
    }
}
