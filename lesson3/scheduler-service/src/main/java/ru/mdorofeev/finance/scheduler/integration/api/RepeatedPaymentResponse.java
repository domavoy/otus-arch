package ru.mdorofeev.finance.scheduler.integration.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class RepeatedPaymentResponse extends Response {

    private Date start;

    private Date end;

    private String granularity;

    private List<RepeatedPayment> repeatedPaymentList = new LinkedList<>();

    public void add(RepeatedPayment repeatedPayment){
        repeatedPaymentList.add(repeatedPayment);
    }

    @ToString
    @NoArgsConstructor
    @Data
    public static class RepeatedPayment {
        private Long userId;
        private Long categoryId;

        private String comment;
        private Double amount;
    }
}
