package ru.mdorofeev.finance.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.api.model.common.Response;
import ru.mdorofeev.finance.api.model.response.AccountListResponse;
import ru.mdorofeev.finance.api.model.response.StringListResponse;

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
    ResponseEntity<Response> addAccount(Long sessionId, @ApiParam(value = "RUB/USD") @RequestParam(required = true) String currency, String name);

}