package ru.mdorofeev.finance.repeated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.common.exception.ServiceException;
import ru.mdorofeev.finance.common.util.DateUtil;
import ru.mdorofeev.finance.repeated.api.RepeatedPaymentController;
import ru.mdorofeev.finance.repeated.api.RepeatedPaymentControllerImpl;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.repeated.api.model.response.RepeatedPaymentResponse;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepeatedPaymentRestTest {

    @Autowired
    private RepeatedPaymentControllerImpl controller;

    @MockBean
    AuthServiceClient authServiceClient;

    @Test
    public void repeatedPaymentTest() throws ServiceException {
        Mockito.when(authServiceClient.findBySession(1L)).thenReturn(1L);

        // jan - none
        Long id = controller.addPayment(new RepeatedPaymentData(1L, 1L,1L,
                100.0, Granularity.NONE.name(), "2020-01-01", "2020-01-30", "comm")).getBody().getResult();

        // feb - monthly
        controller.addPayment(new RepeatedPaymentData(1L, 1L,1L,
                100.0, Granularity.MONTHLY.name(), "2020-02-01", "2020-05-28", ""));

        // march - early
        controller.addPayment(new RepeatedPaymentData(1L, 1L,1L,
                100.0, Granularity.YEARLY.name(), "2020-03-01", "2022-03-28", ""));

//        // none
//        check("2020-01-01", 1);
//        check("2019-01-01", 0);
//        check("2021-01-01", 0);
//
//        // month
//        check("2020-02-01", 1);
        check("2020-05-01", 1);
        check("2020-06-01", 0);
        check("2019-02-01", 0);

        // year
        check("2020-03-01", 2);
        check("2022-03-01", 1);
        check("2023-03-01", 0);
        check("2019-03-01", 0);

        // check one
        ResponseEntity<RepeatedPaymentResponse> data = controller.getPaymentForDate( "2020-01-01");
        Assertions.assertEquals(DateUtil.date("2020-01-01"), data.getBody().getStart());
        Assertions.assertEquals(DateUtil.date("2020-01-01"), data.getBody().getEnd());
        Assertions.assertEquals(Granularity.NONE.toString(), data.getBody().getGranularity());
        Assertions.assertEquals(1, data.getBody().getRepeatedPaymentList().size());
        check(data, 0, 1l, 1l, 1L, 100.0, "comm");

        // update
        controller.updatePayment(new RepeatedPaymentDataUpdate(100L, id, 3L, 3L, 200.0, Granularity.MONTHLY.name(), "2020-01-01", "2021-01-01", "sss"));

        data = controller.getPaymentForDate("2020-01-01");
        check(data, 0, 1l, 3l, 3L, 200.0, "sss");

        // delete
        controller.deletePayment(1L, id);
        check("2020-01-01", 0);
    }

    private void check(ResponseEntity<RepeatedPaymentResponse> data, int index, Long userId, Long categoryId, Long accountId, Double amount, String comment){
        Assertions.assertEquals(categoryId, data.getBody().getRepeatedPaymentList().get(index).getCategoryId());
        Assertions.assertEquals(accountId, data.getBody().getRepeatedPaymentList().get(index).getAccountId());
        Assertions.assertEquals(userId, data.getBody().getRepeatedPaymentList().get(index).getUserId());
        Assertions.assertEquals(comment, data.getBody().getRepeatedPaymentList().get(index).getComment());
        Assertions.assertEquals(amount, data.getBody().getRepeatedPaymentList().get(index).getAmount());
    }

    private void check(String searchDate, int expectedArraySize){
        ResponseEntity<RepeatedPaymentResponse> data = controller.getPaymentForDate(searchDate);
        Assertions.assertEquals(expectedArraySize, data.getBody().getRepeatedPaymentList().size());
    }
}
