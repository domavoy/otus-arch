package ru.mdorofeev.finance.budget;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.budget.api.BudgetControllerImpl;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;

import javax.validation.constraints.AssertTrue;

@ActiveProfiles({"db-h2mem"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BudgetServiceRestTest {

    @Autowired
    private BudgetControllerImpl budgetController;

    @MockBean
    AuthServiceClient authServiceClient;

    @Test
    public void budgetTest() throws ServiceException {
        Mockito.when(authServiceClient.findBySession(1L)).thenReturn(1L);

        // create budget
        budgetController.addBudget(new BudgetData(1L, 1L, 100.0));

        ResponseEntity<LongResponse> budget2Response = budgetController.addBudget(new BudgetData(1L, 2L, 200.0));
        Assertions.assertNotNull(budget2Response);
        Long id2 = budget2Response.getBody().getResult();
        Assertions.assertNotNull(id2);

        // get budget
        ResponseEntity<BudgetResponse> data = budgetController.getBudgetData(1L);
        Assertions.assertEquals(new BudgetResponse.Budget(1L, 1L, 1L, 100.0),
                data.getBody().getBudgetList().get(0), "budget: first");
        Assertions.assertEquals(new BudgetResponse.Budget(2L, 1L, 2L, 200.0),
                data.getBody().getBudgetList().get(1), "budget: second");

        // update budget
        budgetController.updateBudget(new BudgetDataUpdate(100L, id2,44L, 300.0));
        data = budgetController.getBudgetData(1L);
        Assertions.assertEquals(new BudgetResponse.Budget(1L, 1L, 1L, 100.0),
                data.getBody().getBudgetList().get(0), "budget: first");
        Assertions.assertEquals(new BudgetResponse.Budget(2L, 1L, 44L, 300.0),
                data.getBody().getBudgetList().get(1), "budget: second");

        // no data
        data = budgetController.getBudgetData(999L);
        Assertions.assertEquals(0, data.getBody().getBudgetList().size(), "no data");
    }

    @Test
    public void budgetNotFound(){
        ResponseEntity<Response> data = budgetController.updateBudget(new BudgetDataUpdate(100L, 100L, 44L, 300.0));
        Assertions.assertEquals(200, data.getStatusCode().value());
        Assertions.assertNotNull(data.getBody().getError().getCode());
    }
}
