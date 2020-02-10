package ru.mdorofeev.finance.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.external.SbrfCurrencyParser;
import ru.mdorofeev.finance.scheduler.external.ValCurs;
import ru.mdorofeev.finance.scheduler.external.Valute;
import ru.mdorofeev.finance.scheduler.integration.FinanceServiceClient;
import ru.mdorofeev.finance.scheduler.integration.RepeatedServiceClient;
import ru.mdorofeev.finance.scheduler.integration.api.RepeatedPaymentResponse;

import java.util.Date;

@Service
public class ScheduledService {

    @Autowired
    FinanceServiceClient financeServiceClient;

    @Autowired
    RepeatedServiceClient repeatedServiceClient;

    @Autowired
    CurrencyProvider currencyProvider;

    public void executeCurrencyUpload() throws ServiceException{
        ValCurs currency = currencyProvider.getForDate(new Date());
        for(Valute value : currency.getValutes()){
            financeServiceClient.createOrUpdateCurrency(value.charCode, value.value);
        }
    }

    public void executeCreateRepeatedPayment() throws ServiceException{
        RepeatedPaymentResponse data = repeatedServiceClient.getScheduledData(new Date());
        if(data == null){
            throw new ServiceException("executeCreateRepeatedPayment: scheduled data no found");
        }

        for(RepeatedPaymentResponse.RepeatedPayment payment : data.getRepeatedPaymentList()){
            financeServiceClient.createInternalTransaction(payment.getUserId(), payment.getCategoryId(),
                    payment.getAccountId(), payment.getAmount(), payment.getComment());
        }
    }
}
