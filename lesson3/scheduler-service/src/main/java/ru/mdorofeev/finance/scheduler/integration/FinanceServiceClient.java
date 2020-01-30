package ru.mdorofeev.finance.scheduler.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.common.api.model.response.CurrencyResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionListResponse;
import ru.mdorofeev.finance.scheduler.integration.api.TransactionRequest;

@Service
public class FinanceServiceClient {

    @Value("${app.integration.finance-service-rest-base}")
    private String financeServiceBase;

    private RestTemplate restTemplate;

    public static final String financeAddTransactionUri = "/main/addTransaction";
    public static final String financeGetTransactionListUri = "/main/getTransactions?fromDate={fromDate}";

    public static final String financeAddCategory = "/config/addCategory?categoryType={categoryType}&name={categoryName}&sessionId={sessionId}";
    public static final String financeAddAccount = "/config/addAccount?currency={currency}&name={categoryName}&sessionId={sessionid}";

    public static final String createCurrency = "/config/createCurrency?currency={currency}&rate={rate}";
    public static final String getCurrency = "/config/getCurrency?currency={currency}";
    public static final String updateCurrency = "/config/updateCurrency?currencyName={currency}&rate={rate}";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public FinanceServiceClient(@Value("financeServiceBase") String financeServiceBase) {
        this.financeServiceBase = financeServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void createAccount(Long sessionId, String accountName) throws ServiceException {
        Response result = restTemplate.postForEntity(financeServiceBase + financeAddAccount, null,
                Response.class, "RUB", accountName, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getMessage(), result.getError().getCode());
        }
    }

    public void createCategory(Long sessionId, String category) throws ServiceException {
        Response result = restTemplate.postForEntity(financeServiceBase + financeAddCategory, null,
                Response.class, "INCOME", category, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getMessage(), result.getError().getCode());
        }
    }

    public void createTransaction(Long sessionId, String categoryName, String accountName, Double money, String comment) throws ServiceException {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setSessionId(sessionId);
        transactionRequest.setAccountName(accountName);
        transactionRequest.setCategoryName(categoryName);
        transactionRequest.setComment(comment);
        transactionRequest.setMoney(money);

        Response result = restTemplate.postForEntity(financeServiceBase + financeAddTransactionUri,
                transactionRequest, Response.class).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
    }

    public TransactionListResponse getTransactions(String forDate) throws ServiceException {
        TransactionListResponse result = restTemplate.getForEntity(financeServiceBase + financeGetTransactionListUri,
                TransactionListResponse.class, forDate).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }

        return result;
    }

    public void updateCurrency(String currencyName, Double rate) throws ServiceException {
        Response result = restTemplate.getForEntity(financeServiceBase + updateCurrency,
                Response.class, currencyName, rate).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }
    }

    public void createCurrency(String currencyName, Double rate) throws ServiceException {
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
