package ru.mdorofeev.finance.budget.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BudgetData {

    private Long userId;

    private Long categoryId;

    private Double amount;
}
