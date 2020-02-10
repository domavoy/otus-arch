package ru.mdorofeev.finance.repeated.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepeatedPaymentDataUpdate {

    private long sessionId;

    private Long repeatedPaymentId;

    private Long categoryId;

    private Long accountId;

    private Double amount;

    private String granularity;

    private String start;

    private String end;

    private String comment;
}
