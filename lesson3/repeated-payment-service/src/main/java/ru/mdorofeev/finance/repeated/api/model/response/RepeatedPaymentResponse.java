package ru.mdorofeev.finance.repeated.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.repeated.persistence.dict.Granularity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class RepeatedPaymentResponse extends Response{

    private Date start;

    private Date end;

    private Granularity granularity;

    private List repeatedPaymentList = new LinkedList();

    public void add(RepeatedPayment repeatedPayment){
        repeatedPaymentList.add(repeatedPayment);
    }

    public class RepeatedPayment {
        private Long userId;
        private Long categoryId;

        private String name;
        private Double amount;
    }
}
