package ru.mdorofeev.finance.repeated;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.repeated.api.RepeatedPaymentControllerImpl;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;

import java.time.LocalDate;
import java.util.Date;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepeatedPaymentRestTest {

    @Autowired
    private RepeatedPaymentControllerImpl repeatedPaymentController;

    @Test
    public void repeatedPaymentTest(){

        // jan - none
        repeatedPaymentController.addPayment(new RepeatedPaymentData(1L, 1L,
                100.0, Granularity.NONE.name(), DateUtil.date("2020-01-01"), DateUtil.date("2020-01-30"), ""));

        // feb - monthly
        repeatedPaymentController.addPayment(new RepeatedPaymentData(1L, 1L,
                100.0, Granularity.MONTHLY.name(), DateUtil.date("2020-02-01"), DateUtil.date("2020-02-28"), ""));

        // march - early
        repeatedPaymentController.addPayment(new RepeatedPaymentData(1L, 1L,
                100.0, Granularity.YEARLY.name(), DateUtil.date("2020-03-01"), DateUtil.date("2020-03-28"), ""));


    }


}
