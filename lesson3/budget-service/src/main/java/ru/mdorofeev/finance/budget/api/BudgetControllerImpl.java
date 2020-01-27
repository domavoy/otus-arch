package ru.mdorofeev.finance.budget.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.LongResponse;
import ru.mdorofeev.finance.budget.persistence.Budget;
import ru.mdorofeev.finance.budget.service.BudgetService;
import ru.mdorofeev.finance.common.api.Processor;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.List;


@Service
public class BudgetControllerImpl implements BudgetController {

    private static final Logger LOG = LoggerFactory.getLogger(BudgetControllerImpl.class);

    @Autowired
    BudgetService budgetService;

    @Override
    public ResponseEntity<LongResponse> addBudget(BudgetData body) {
        return Processor.wrapExceptions(() -> {
            Long id = budgetService.addBudget(body.getUserId(), body.getCategoryId(), body.getAmount());
            return new ResponseEntity<>(new LongResponse(id), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> updateBudget(BudgetDataUpdate body) {
        return Processor.wrapExceptions(() -> {
            budgetService.updateBudget(body.getBudgetId(), body.getCategoryId(), body.getAmount());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<BudgetResponse> getBudgetData(Long userId) {
        return Processor.wrapExceptions(() -> {
            List<Budget> data = budgetService.findByUserId(userId);

            BudgetResponse budgetResponse = new BudgetResponse();
            for(Budget budget : data){
                budgetResponse.add(new BudgetResponse.Budget(budget.getId(), budget.getUserId(),
                        budget.getCategoryId(), budget.getAmount())
                );
            }

            return new ResponseEntity<>(budgetResponse, HttpStatus.OK);
        });
    }
}