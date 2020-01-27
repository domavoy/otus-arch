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
public class InfinitePaymentData {

    private Long userId;

    private Long categoryId;

    private Double amount;

    private String granularity;

    private String comment;
}
