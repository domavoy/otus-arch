package ru.mdorofeev.finance.budget.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.budget.api.model.request.BudgetData;
import ru.mdorofeev.finance.budget.api.model.request.BudgetDataUpdate;
import ru.mdorofeev.finance.budget.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.budget.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.budget.api.model.response.BudgetResponse;
import ru.mdorofeev.finance.budget.api.model.response.RepeatedPaymentResponse;
import ru.mdorofeev.finance.budget.api.model.response.Response;

import java.util.Date;

@RestController
@RequestMapping(name = "budget controller", produces = "application/json", path = "budget")
@Api(value = "Finance app budget REST API", tags = {"Repeated payment management", "Budget management"})
//TODO: P1: userId => session as in finance service
public interface BudgetController {

    @PostMapping("/addRepeatedPayment")
    @ApiOperation(value = "Add repeated payment", tags = "Repeated payment management")
    ResponseEntity<Response> addRepeatedPayment(@RequestBody RepeatedPaymentData body);

    @PostMapping("/updateRepeatedPayment")
    @ApiOperation(value = "Update repeated payment data", tags = "Repeated payment management")
    ResponseEntity<Response> updateRepeatedPaymentData(@RequestBody RepeatedPaymentDataUpdate body);

    @GetMapping("/getRepeatedPayments")
    @ApiOperation(value = "Get repeated data", tags = "Repeated payment management")
    ResponseEntity<RepeatedPaymentResponse> getRepeatedData(Long userId, Date start, Date end, String granularity);



    @PostMapping("/addBudget")
    @ApiOperation(value = "Add budget", tags = "Budget management")
    ResponseEntity<Response> addBudget(@RequestBody BudgetData body);

    @PostMapping("/updateBudget")
    @ApiOperation(value = "Update repeated payment data", tags = "Budget management")
    ResponseEntity<Response> updateBudget(@RequestBody BudgetDataUpdate body);

    @GetMapping("/getBudget")
    @ApiOperation(value = "Get repeated data", tags = "Budget management")
    ResponseEntity<BudgetResponse> getBudgetData(Long userId);
}