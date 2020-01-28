package ru.mdorofeev.finance.repeated.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.api.Processor;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.repeated.api.model.request.FuturePaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.InfinitePaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.repeated.api.model.response.RepeatedPaymentResponse;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;
import ru.mdorofeev.finance.repeated.service.RepeatedPaymentService;

import java.util.Date;
import java.util.List;


@Service
public class RepeatedPaymentControllerImpl implements RepeatedPaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(RepeatedPaymentControllerImpl.class);

    @Autowired
    RepeatedPaymentService paymentService;

    @Override
    public ResponseEntity<LongResponse> addFuturePayment(FuturePaymentData body) {
        return addPayment(new RepeatedPaymentData(body.getUserId(), body.getCategoryId(),
                body.getAmount(), Granularity.NONE.name(), new Date(), null, body.getComment()));
    }

    @Override
    public ResponseEntity<LongResponse> addInfinitePayment(InfinitePaymentData body) {
        return addPayment(new RepeatedPaymentData(body.getUserId(), body.getCategoryId(),
                body.getAmount(), body.getGranularity(), new Date(), null, body.getComment()));
    }

    @Override
    public ResponseEntity<LongResponse> addPayment(RepeatedPaymentData body) {
        return Processor.wrapExceptions(() -> {

            Granularity granularity;
            try {
                granularity = Granularity.from(body.getGranularity());
            } catch (ServiceException e){
                throw new ServiceException("Incorrect granularity: should be: " + Granularity.values());
            }

            Long id = paymentService.addPayment(body.getUserId(), body.getCategoryId(),
                    body.getAmount(), granularity, body.getStart(), body.getEnd(), body.getComment());
            return new ResponseEntity<>(new LongResponse(id), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> updatePayment(RepeatedPaymentDataUpdate body) {
        return Processor.wrapExceptions(() -> {
            Granularity granularity;
            try {
                granularity = Granularity.from(body.getGranularity());
            } catch (ServiceException e){
                throw new ServiceException("Incorrect granularity: should be: " + Granularity.values());
            }

            paymentService.updatePayment(body.getRepeatedPaymentId(), body.getCategoryId(),
                    body.getAmount(), granularity, body.getStart(), body.getEnd(), body.getComment());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> deletePayment(Long userId, Long paymentId) {
        return Processor.wrapExceptions(() -> {
            paymentService.removePayment(paymentId);
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<RepeatedPaymentResponse> getPaymentForDate(Long userId, Date date) {
        return Processor.wrapExceptions(() -> {
            List<RepeatedPayment> data = paymentService.findForDate(date, userId);
            RepeatedPaymentResponse response = new RepeatedPaymentResponse();
            response.setStart(date);
            response.setEnd(date);
            response.setGranularity(Granularity.NONE);

            for(RepeatedPayment repeatedPayment : data){
                response.add(to(repeatedPayment));
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }

    private RepeatedPaymentResponse.RepeatedPayment to(RepeatedPayment repeatedPayment){
        RepeatedPaymentResponse.RepeatedPayment payment = new RepeatedPaymentResponse.RepeatedPayment();
        payment.setUserId(repeatedPayment.getUserId());
        payment.setCategoryId(repeatedPayment.getCategoryId());
        payment.setAmount(repeatedPayment.getAmount());
        payment.setComment(repeatedPayment.getComment());
        return payment;
    }

//    @Override
//    //https://stackoverflow.com/questions/45060886/grouping-items-by-date
//    public ResponseEntity<RepeatedPaymentResponse> getPaymentData(Long userId, Date start, Date end, String granularityStr) {
//        return Processor.wrapExceptions(() -> {
//            Granularity granularity;
//            try {
//                granularity = Granularity.from(granularityStr);
//            } catch (ServiceException e){
//                throw new ServiceException("Incorrect granularity: should be: " + Granularity.values());
//            }
//
//            List<RepeatedPayment> data = paymentService.findByUserId(userId, start, end);
//            if(granularity == Granularity.NONE){
//                RepeatedPaymentResponse response = new RepeatedPaymentResponse();
//                response.setStart(start);
//                response.setEnd(end);
//                response.setGranularity(granularity);
//                for(RepeatedPayment repeatedPayment : data){
//                    RepeatedPaymentResponse.RepeatedPayment payment = new RepeatedPaymentResponse.RepeatedPayment();
//                    payment.setUserId(repeatedPayment.getUserId());
//                    payment.setCategoryId(repeatedPayment.getCategoryId());
//                    payment.setAmount(repeatedPayment.getAmount());
//                    payment.setComment(repeatedPayment.getComment());
//                }
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            }
//
//
//
//
//            return new ResponseEntity<RepeatedPaymentResponse>(new RepeatedPaymentResponse(), HttpStatus.OK);
//        });
//    }
}