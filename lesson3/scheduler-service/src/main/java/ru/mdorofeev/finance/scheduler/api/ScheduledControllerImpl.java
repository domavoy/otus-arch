package ru.mdorofeev.finance.scheduler.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.api.model.response.ErrorResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.scheduler.service.ScheduledService;


@Service
public class ScheduledControllerImpl implements ScheduledController {

    @Autowired
    ScheduledService scheduledService;

    @Override
    public ResponseEntity<Response> executeCurrencyUpload() {
        try {
            scheduledService.executeCurrencyUpload();
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new Response(new ErrorResponse(e.getCode(), e.getMessage())), HttpStatus.resolve(500));
        }
    }

    @Override
    public ResponseEntity<Response> executeCreateRepeatedPayment() {
        try {
            scheduledService.executeCreateRepeatedPayment();
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new Response(new ErrorResponse(e.getCode(), e.getMessage())), HttpStatus.resolve(500));
        }
    }
}