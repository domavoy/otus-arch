package ru.mdorofeev.finance.scheduler.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.integration.api.InfinitePaymentData;
import ru.mdorofeev.finance.scheduler.integration.api.RepeatedPaymentResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RepeatedServiceClient {

    @Value("${app.integration.repeated-payment-service-config-rest-base}")
    private String repeatedServiceBase;

    private RestTemplate restTemplate;

    public static final String addInfitePayment = "/addInfinitePayment/";
    public static final String getPayments = "/getForDate?dateStr={forDate}";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public RepeatedServiceClient(@Value("repeatedServiceBase") String repeatedServiceBase) {
        this.repeatedServiceBase = repeatedServiceBase;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public void addScheduledData(Long sessionId, Long categoryId, Long accountId, Double amount, String date) throws ServiceException {
        InfinitePaymentData infinitePaymentData = new InfinitePaymentData();
        infinitePaymentData.setSessionId(sessionId);
        infinitePaymentData.setAmount(amount);
        infinitePaymentData.setAccountId(accountId);
        infinitePaymentData.setCategoryId(10L);
        infinitePaymentData.setGranularity("MONTHLY");
        infinitePaymentData.setDate(date);

        Response result = restTemplate.postForEntity(repeatedServiceBase + addInfitePayment, infinitePaymentData,
                Response.class, "INCOME", categoryId, sessionId).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getMessage(), result.getError().getCode());
        }
    }

    public RepeatedPaymentResponse getScheduledData(Date date) throws ServiceException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        RepeatedPaymentResponse result = restTemplate.getForEntity(repeatedServiceBase + getPayments,
                RepeatedPaymentResponse.class, format.format(date)).getBody();
        if(result.getError() != null){
            throw new ServiceException(result.getError().getCode());
        }

        return result;
    }
}
