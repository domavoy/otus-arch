package ru.mdorofeev.finance.budget.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
//TODO: FEATURE: BUDGET: add start/end and granularity
public class BudgetResponse extends Response{

    private List budgetList = new LinkedList();

    public void add(Budget budget){
        budgetList.add(budget);
    }

    public List getBudgetList() {
        return budgetList;
    }

    @ToString
    @NoArgsConstructor
    @Data
    public class Budget {
        private Long categoryId;
        private Double amount;
    }
}
