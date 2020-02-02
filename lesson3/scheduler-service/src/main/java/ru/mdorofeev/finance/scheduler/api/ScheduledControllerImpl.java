package ru.mdorofeev.finance.scheduler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.api.Processor;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.scheduler.service.ScheduledService;


@Service
public class ScheduledControllerImpl implements ScheduledController {

    @Autowired
    ScheduledService scheduledService;

    @Scheduled(cron = "${app.scheduling.currencyUpdateCron}")
    @Override
    public ResponseEntity<Response> executeCurrencyUpload() {
        return Processor.wrapExceptions(() -> {
            scheduledService.executeCurrencyUpload();
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Scheduled(cron = "${app.scheduling.createRepeatedPaymentCron}")
    @Override
    public ResponseEntity<Response> executeCreateRepeatedPayment() {
        return Processor.wrapExceptions(() -> {
            scheduledService.executeCreateRepeatedPayment();
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }
}