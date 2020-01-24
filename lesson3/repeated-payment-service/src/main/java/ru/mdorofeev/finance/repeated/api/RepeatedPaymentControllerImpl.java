package ru.mdorofeev.finance.repeated.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.repeated.api.model.response.RepeatedPaymentResponse;
import ru.mdorofeev.finance.repeated.api.model.response.Response;

import java.util.Date;


@Service
public class RepeatedPaymentControllerImpl implements RepeatedPaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(RepeatedPaymentControllerImpl.class);

    @Override
    public ResponseEntity<Response> addRepeatedPayment(RepeatedPaymentData body) {
        return null;
    }

    @Override
    public ResponseEntity<Response> updateRepeatedPaymentData(RepeatedPaymentDataUpdate body) {
        return null;
    }

    @Override
    public ResponseEntity<RepeatedPaymentResponse> getRepeatedData(Long userId, Date start, Date end, String granularity) {
        return null;
    }
}