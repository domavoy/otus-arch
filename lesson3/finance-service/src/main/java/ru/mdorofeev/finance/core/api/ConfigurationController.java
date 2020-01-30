package ru.mdorofeev.finance.core.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.common.api.model.response.CurrencyResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.core.api.model.response.AccountListResponse;
import ru.mdorofeev.finance.core.api.model.response.StringListResponse;

@RestController
@RequestMapping(name = "finance controller", produces = "application/json", path = "config")
@Api(value = "Finance app configuration REST API", tags = "App configuration")
public interface ConfigurationController {

    @GetMapping("/getCategories")
    @ApiOperation(value = "Get categories")
    ResponseEntity<StringListResponse> getCategories(Long sessionId);

    @PostMapping("/addCategory")
    @ApiOperation(value = "Add category", tags = "App configuration")
    ResponseEntity<Response> addCategory(Long sessionId, @ApiParam(value = "INCOME/EXPENSE") @RequestParam(required = true) String categoryType, String name);

    @GetMapping("/getAccounts")
    @ApiOperation(value = "Get accounts", tags = "App configuration")
    ResponseEntity<AccountListResponse> getAccounts(Long sessionId);

    @PostMapping("/addAccount")
    @ApiOperation(value = "Add account", tags = "App configuration")
    ResponseEntity<Response> addAccount(Long sessionId, @ApiParam(value = "RUB/USD/EUR") @RequestParam(required = true) String currency, String name);

    //TODO: P1: secure
    @GetMapping("/updateCurrency")
    @ApiOperation(value = "Update currency", tags = "App configuration")
    ResponseEntity<Response> updateCurrency(String currency, Double rate);

    //TODO: P1: secure
    @GetMapping("/getCurrency")
    @ApiOperation(value = "Get currency", tags = "App configuration")
    ResponseEntity<CurrencyResponse> getCurrency(String currency);

    //TODO: P1: secure
    @GetMapping("/createCurrency")
    @ApiOperation(value = "Create currency", tags = "App configuration")
    ResponseEntity<Response> createCurrency(String currency, Double rate);
}