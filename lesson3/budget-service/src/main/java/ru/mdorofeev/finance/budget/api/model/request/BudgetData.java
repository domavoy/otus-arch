package ru.mdorofeev.finance.budget.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.budget.api.model.Granularity;

import java.util.Date;

@ToString
@NoArgsConstructor
@Data
public class BudgetData {

    private Long userId;

    private Long categoryId;

    private Double amount;

    private Granularity granularity;

    private Date start;

    private Date end;
}
