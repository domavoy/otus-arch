package ru.mdorofeev.finance.budget.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.budget.persistence.dict.Granularity;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepeatedPaymentData {

    private Long userId;

    private Long categoryId;

    private Double amount;

    private String granularity;

    private Date start;

    private Date end;
}
