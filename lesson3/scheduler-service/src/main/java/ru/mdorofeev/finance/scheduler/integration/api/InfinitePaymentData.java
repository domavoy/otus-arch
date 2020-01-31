package ru.mdorofeev.finance.scheduler.integration.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InfinitePaymentData {

    private Long sessionId;

    private String date;

    private Long categoryId;

    private Long accountId;

    private Double amount;

    private String granularity;

    private String comment;
}
