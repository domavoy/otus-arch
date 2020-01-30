package ru.mdorofeev.finance.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.integration.FinanceServiceClient;
import ru.mdorofeev.finance.scheduler.integration.RepeatedServiceClient;

@Service
public class ScheduledService {

    @Autowired
    FinanceServiceClient financeServiceClient;

    @Autowired
    RepeatedServiceClient repeatedServiceClient;

    public void executeCurrencyUpload() throws ServiceException {

    }

    public void executeCreateRepeatedPayment() throws ServiceException{

    }
}
