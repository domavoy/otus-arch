package ru.mdorofeev.finance.budget.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetDataUpdate {

    private Long sessionId;

    private Long budgetId;

    private Long categoryId;

    private Double amount;
}
