package ru.mdorofeev.finance.repeated.service;

import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IntervalSearchBean {

    public List<RepeatedPayment> findForDay(Date findDate, List<RepeatedPayment> request){
        List<RepeatedPayment> data = new ArrayList<>();

        for(RepeatedPayment event : request){
            Date eventStartDate = event.getStart();
            Date evenEndDate = event.getEnd();

            if(eventStartDate.compareTo(findDate) > 0){
                continue;
            }
            if(evenEndDate != null && evenEndDate.compareTo(findDate) < 0){
                continue;
            }

            if(event.getGranularity().equals(Granularity.NONE.getId())){
                if(eventStartDate.getDate() == findDate.getDate() && eventStartDate.getMonth() == findDate.getMonth() && eventStartDate.getYear() == findDate.getYear()){
                    data.add(event);
                }
            }

            if(event.getGranularity().equals(Granularity.MONTHLY.getId())){
                if(eventStartDate.getDate() == findDate.getDate()){
                    data.add(event);
                }
            }

            if(event.getGranularity().equals(Granularity.YEARLY.getId())){
                if(eventStartDate.getDate() == findDate.getDate() && eventStartDate.getMonth() == findDate.getMonth()){
                    data.add(event);
                }
            }
        }

        return data;
    }
}
