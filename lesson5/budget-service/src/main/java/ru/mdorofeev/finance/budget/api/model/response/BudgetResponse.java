package ru.mdorofeev.finance.budget.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
//TODO: FEATURE: BUDGET: add start/end and granularity
public class BudgetResponse extends Response {

    private List<Budget> budgetList = new LinkedList();

    public void add(Budget budget){
        budgetList.add(budget);
    }

    public List<Budget> getBudgetList() {
        return budgetList;
    }

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Budget {
        private Long budgetId;
        private Long userId;
        private Long categoryId;
        private Double amount;
    }
}
