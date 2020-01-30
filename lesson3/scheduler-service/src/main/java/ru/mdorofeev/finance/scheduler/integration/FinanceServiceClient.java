package ru.mdorofeev.finance.scheduler.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FinanceServiceClient {

    private final String financeServiceBase;
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public FinanceServiceClient(@Value("financeServiceBase") String financeServiceBase) {
        this.financeServiceBase = financeServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void createTransaction(Long userId){
        // pass all transactions
    }

    public void updateCurrency(String currencyName, Double value){
        // create method on finance
        // finance currency => manual field
        // update all not manual
    }
}
