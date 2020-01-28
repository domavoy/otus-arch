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

    private Double amount;

    private String granularity;

    private Date start;

    private Date end;

    private String comment;
}
