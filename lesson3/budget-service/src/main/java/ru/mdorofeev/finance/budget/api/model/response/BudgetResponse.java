package ru.mdorofeev.finance.budget.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.mdorofeev.finance.budget.api.model.Granularity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

@ToString
@NoArgsConstructor
@Data
public class BudgetResponse extends Response{

    private Date start;

    private Date end;

    private Granularity granularity;

    private List budgetList = new LinkedList();

    public void add(Budget budget){
        budgetList.add(budget);
    }

    public class Budget{
        private Long userId;
        private Long categoryId;

        private String name;
        private Double amount;
    }
}
