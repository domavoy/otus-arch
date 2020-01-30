package ru.mdorofeev.finance.repeated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;
import ru.mdorofeev.finance.repeated.service.IntervalSearchBean;

import java.util.*;

public class IntervalTest {

    @Test
    public void test() {
        RepeatedPayment noneOne = build(DateUtil.date("2019-01-03"), null, Granularity.NONE);
        RepeatedPayment none = build(DateUtil.date("2019-01-05"), null, Granularity.NONE);
        RepeatedPayment monthly5Feb = build(DateUtil.date("2019-03-05"), null, Granularity.MONTHLY);
        RepeatedPayment monthly5March = build(DateUtil.date("2019-03-05"), null, Granularity.MONTHLY);
        RepeatedPayment monthly10March = build(DateUtil.date("2019-03-10"), null, Granularity.MONTHLY);
        RepeatedPayment yearly5March = build(DateUtil.date("2019-03-05"), null, Granularity.MONTHLY);
        RepeatedPayment yearly10April = build(DateUtil.date("2019-04-10"), null, Granularity.MONTHLY);

        RepeatedPayment noneEndDate = build(DateUtil.date("2019-01-06"), DateUtil.date("2019-12-31"), Granularity.NONE);
        RepeatedPayment monthly13MayEndDate = build(DateUtil.date("2019-04-13"), DateUtil.date("2019-12-31"), Granularity.MONTHLY);
        RepeatedPayment yearly18MayEndDate = build(DateUtil.date("2019-04-18"), DateUtil.date("2023-12-31"), Granularity.YEARLY);

        List<RepeatedPayment> data = Arrays.asList(noneOne, none, monthly5Feb, monthly5March, monthly10March,
                yearly5March, yearly10April, noneEndDate, monthly13MayEndDate, yearly18MayEndDate
        );

        checkEvent("2019-01-03", data, noneOne);
        checkEvent("2019-01-06", data, noneEndDate);
        checkEvent("2020-01-06", data);
        checkEvent("2018-01-06", data);

        checkEvent("2019-04-13", data, monthly13MayEndDate);
        checkEvent("2019-06-13", data, monthly13MayEndDate);
        checkEvent("2018-04-13", data);
        checkEvent("2020-04-13", data);

        checkEvent("2019-04-18", data, yearly18MayEndDate);
        checkEvent("2022-04-18", data, yearly18MayEndDate);
        checkEvent("2018-04-18", data);
        checkEvent("2024-04-13", data);
    }

    private void checkEvent(String findDate, List<RepeatedPayment> data, RepeatedPayment ... expected){
        IntervalSearchBean bean = new IntervalSearchBean();
        Assertions.assertArrayEquals(expected, bean.findForDay(DateUtil.date(findDate), data).toArray());
    }

    private RepeatedPayment build(Date start, Date end, Granularity granularity){
        RepeatedPayment repeatedPayment= new RepeatedPayment();
        repeatedPayment.setStart(start);
        repeatedPayment.setEnd(end);
        repeatedPayment.setGranularity(granularity.getId());
        return repeatedPayment;
    }
}
