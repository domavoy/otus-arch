package ru.mdorofeev.finance.scheduler.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RepeatedServiceClient {

    private final String repeatedServiceBase;
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public RepeatedServiceClient(@Value("repeatedServiceBase") String repeatedServiceBase) {
        this.repeatedServiceBase = repeatedServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void getScheduledData(Long userId){
        // pass all transactions
    }
}
