package ru.mdorofeev.finance.budget.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Data
public class BudgetDataUpdate extends BudgetData {

    private Long budgetId;
}
