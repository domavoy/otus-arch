package ru.mdorofeev.finance.budget.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetDataUpdate extends BudgetData{

    private Long budgetId;
}
