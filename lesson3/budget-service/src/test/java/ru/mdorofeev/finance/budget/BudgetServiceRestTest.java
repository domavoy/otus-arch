package ru.mdorofeev.finance.budget;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.budget.api.BudgetControllerImpl;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.LongResponse;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceRestTest {

    @Autowired
    private BudgetControllerImpl budgetController;

    @Test
    public void budgetTest(){

        // create budget
        budgetController.addBudget(new BudgetData(1L, 1L, 100.0));

        ResponseEntity<LongResponse> budget2Response = budgetController.addBudget(new BudgetData(1L, 2L, 200.0));
        Assertions.assertNotNull(budget2Response);
        Long id2 = budget2Response.getBody().getResult();
        Assertions.assertNotNull(id2);

        // get budget
        ResponseEntity<BudgetResponse> data = budgetController.getBudgetData(1L);
        Assertions.assertEquals(new BudgetData(1L, 1L, 100.0),
                data.getBody().getBudgetList().get(0), "budget: first");
        Assertions.assertEquals(new BudgetData(1L, 2L, 200.0),
                data.getBody().getBudgetList().get(0), "budget: second");

        // update budget
        budgetController.updateBudget(new BudgetDataUpdate(id2,44L, 300.0));
        data = budgetController.getBudgetData(1L);
        Assertions.assertEquals(new BudgetData(1L, 1L, 100.0),
                data.getBody().getBudgetList().get(0), "budget: first");
        Assertions.assertEquals(new BudgetData(1L, 44L, 300.0),
                data.getBody().getBudgetList().get(0), "budget: second");
    }
}
