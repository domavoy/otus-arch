package ru.mdorofeev.finance.budget.api.model.request;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BudgetData {

    private Long sessionId;

    private Long categoryId;

    private Double amount;
}
