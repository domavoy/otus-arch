package ru.mdorofeev.finance.repeated.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;
import ru.mdorofeev.finance.repeated.repository.RepeatedRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RepeatedPaymentService {

    @Autowired
    RepeatedRepository repeatedRepository;

    @Autowired
    IntervalSearchBean intervalSearchBean;

    public Long addPayment(Long userId, Long categoryId, Long accountId, Double amount, Granularity granularity, Date start, Date end, String comment){
        RepeatedPayment payment = new RepeatedPayment();
        payment.setUserId(userId);
        payment.setCategoryId(categoryId);
        payment.setAccountId(accountId);
        payment.setAmount(amount);
        payment.setGranularity(granularity.getId());
        payment.setStart(start);
        payment.setEnd(end);
        payment.setComment(comment);

        return repeatedRepository.save(payment).getId();
    }

    public void updatePayment(Long paymentId, Long categoryId, Long accountId, Double amount, Granularity granularity, Date start, Date end, String comment) throws ServiceException {
        Optional<RepeatedPayment> optionalPayment = repeatedRepository.findById(paymentId);
        if(optionalPayment.isPresent()){
            RepeatedPayment payment = optionalPayment.get();
            payment.setCategoryId(categoryId);
            payment.setAccountId(accountId);
            payment.setAmount(amount);
            payment.setGranularity(granularity.getId());
            payment.setStart(start);
            payment.setEnd(end);
            payment.setComment(comment);
            repeatedRepository.save(payment);
        } else {
            throw new ServiceException("PAYMENT_NOT_FOUND");
        }
    }

    public void removePayment(Long repeatedPaymentId){
        repeatedRepository.deleteById(repeatedPaymentId);
    }

    public List<RepeatedPayment> findForDate(Date date){
        List<RepeatedPayment> data = repeatedRepository.findAllByDate(date);
        return intervalSearchBean.findForDay(date, data);
    }
}
