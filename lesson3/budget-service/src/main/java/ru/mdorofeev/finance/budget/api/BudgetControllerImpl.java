package ru.mdorofeev.finance.budget.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.Response;

import java.util.Date;


@Service
public class BudgetControllerImpl implements BudgetController {

    private static final Logger LOG = LoggerFactory.getLogger(BudgetControllerImpl.class);

    @Override
    public ResponseEntity<Response> addBudget(BudgetData body) {
        return null;
    }

    @Override
    public ResponseEntity<BudgetResponse> getData(Date start, Date end, String granularity) {
        return null;
    }

    @Override
    public ResponseEntity<Response> updateBudgetData(BudgetDataUpdate body) {
        return null;
    }
}