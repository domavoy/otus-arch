package ru.mdorofeev.finance.budget.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.Date;


@Service
public class BudgetControllerImpl implements BudgetController {

    private static final Logger LOG = LoggerFactory.getLogger(BudgetControllerImpl.class);

    @Override
    public ResponseEntity<LongResponse> addBudget(BudgetData body) {
        return null;
    }

    @Override
    public ResponseEntity<Response> updateBudget(BudgetDataUpdate body) {
        return null;
    }

    @Override
    public ResponseEntity<BudgetResponse> getBudgetData(Long userId) {
        return null;
    }
}