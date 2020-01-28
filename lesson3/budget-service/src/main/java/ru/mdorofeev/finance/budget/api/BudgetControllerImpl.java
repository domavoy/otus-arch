package ru.mdorofeev.finance.budget.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.auth.client.AuthServiceClient;
import ru.mdorofeev.finance.auth.client.ProcessWithUserWrapper;
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

    @Value("${app.integration.auth-service-rest.base}")
    private String authServiceBase;

    @Autowired
    BudgetService budgetService;

    @Autowired
    private AuthServiceClient authServiceClient;

    @Bean
    public AuthServiceClient authServiceClient() {
        return new AuthServiceClient(authServiceBase);
    }

    @Override
    public ResponseEntity<LongResponse> addBudget(BudgetData body) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, body.getSessionId(), userId -> {
            Long id = budgetService.addBudget(userId, body.getCategoryId(), body.getAmount());
            return new ResponseEntity<LongResponse>(new LongResponse(id), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Response> updateBudget(BudgetDataUpdate body) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, body.getSessionId(), userId -> {
            budgetService.updateBudget(body.getBudgetId(), body.getCategoryId(), body.getAmount());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<BudgetResponse> getBudgetData(Long sessionId) {
        return ProcessWithUserWrapper.wrapExceptionsAndAuth(authServiceClient, sessionId, userId -> {
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