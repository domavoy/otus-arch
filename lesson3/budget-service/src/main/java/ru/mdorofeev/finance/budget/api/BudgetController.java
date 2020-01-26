package ru.mdorofeev.finance.budget.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;

import java.util.Date;

@RestController
@RequestMapping(name = "budget controller", produces = "application/json", path = "budget")
@Api(value = "Finance app budget REST API", tags = {"Budget management"})
//TODO: P1: userId => session as in finance service
public interface BudgetController {

    @PostMapping("/addBudget")
    @ApiOperation(value = "Add budget", tags = "Budget management")
    ResponseEntity<LongResponse> addBudget(@RequestBody BudgetData body);

    @PostMapping("/updateBudget")
    @ApiOperation(value = "Update budget data", tags = "Budget management")
    ResponseEntity<Response> updateBudget(@RequestBody BudgetDataUpdate body);

    @GetMapping("/getBudget")
    @ApiOperation(value = "Get budget data", tags = "Budget management")
    ResponseEntity<BudgetResponse> getBudgetData(Long userId);
}