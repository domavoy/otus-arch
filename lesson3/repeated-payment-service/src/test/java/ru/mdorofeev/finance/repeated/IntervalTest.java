package ru.mdorofeev.finance.repeated;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;

import java.util.*;

@Disabled
public class IntervalTest {

    @Test
    public void test(){
        List<Event> data = Arrays.asList(
                new Event(DateUtil.date("2019-01-05"), null, Granularity.NONE),
                new Event(DateUtil.date("2019-02-05"), null, Granularity.MONTHLY),
                new Event(DateUtil.date("2018-02-05"), null, Granularity.MONTHLY),
                new Event(DateUtil.date("2019-03-05"), null, Granularity.YEARLY),
                new Event(DateUtil.date("2018-03-05"), null, Granularity.YEARLY)
        );

        System.out.println(findForDay(DateUtil.date("2019-01-05"), data));



    }

    private List<Event> findForDay(Date date, List<Event> request){
        List<Event> data = new ArrayList<>();

        for(Event req : request){
            if(req.getGranularity().equals(Granularity.NONE)){
                Date start = req.getStart();
                if(start.getDay() == date.getDay() && start.getMonth() == date.getMonth() && start.getYear() == date.getYear()){
                    data.add(req);
                }
            }

            if(req.getGranularity().equals(Granularity.MONTHLY)){
                Date start = req.getStart();
                if(start.getDay() == date.getDay()){
                    data.add(req);
                }
            }

            if(req.getGranularity().equals(Granularity.YEARLY)){
                Date start = req.getStart();
                if(start.getDay() == date.getDay() && start.getMonth() == date.getMonth()){
                    data.add(req);
                }
            }
        }

        return data;
    }


    @AllArgsConstructor
    @Data
    private class Event {
        private Date start;
        private Date end;
        private Granularity granularity;
    }
}
