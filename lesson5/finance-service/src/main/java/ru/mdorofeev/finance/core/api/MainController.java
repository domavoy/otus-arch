package ru.mdorofeev.finance.core.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.core.api.model.request.TransactionRequest;
import ru.mdorofeev.finance.core.api.model.request.TransactionRequestInternal;
import ru.mdorofeev.finance.core.api.model.request.TransactionTransferRequest;
import ru.mdorofeev.finance.core.api.model.response.AccountStatListResponse;
import ru.mdorofeev.finance.core.api.model.response.TransactionListResponse;
import ru.mdorofeev.finance.core.expimp.UploadFileResponse;

//TODO: P2: move 'main' to yml
@RestController
@RequestMapping(name = "finance controller", produces = "application/json", path = "main")
@Api(value = "Finance app main REST API", tags = "Basic operations")
public interface MainController {

    //TODO: P3: pass date
    @GetMapping("/getTransactions")
    @ApiOperation(value = "Get transactions", tags = "Basic operations")
    ResponseEntity<TransactionListResponse> getTransactions(Long sessionId, @ApiParam(value = "2019-01-10") @RequestParam(required = true) String date);

    @PostMapping("/addTransaction")
    @ApiOperation(value = "Add transaction", tags = "Basic operations")
    ResponseEntity<Response> addTransaction(@RequestBody TransactionRequest request);

    //TODO: P2: FEATURE: doesn't work with RUB => USD
    @PostMapping("/accountMoneyTransfer")
    @ApiOperation(value = "Money transfer between accounts", tags = "Basic operations")
    ResponseEntity<Response> moneyTransfer(@RequestBody TransactionTransferRequest request);

    @GetMapping("/getAccountStat")
    @ApiOperation(value = "Get account status", tags = "Basic operations")
    ResponseEntity<AccountStatListResponse> getAccountStat(Long sessionId);


    //TODO: P1: secure for internal usage
    @PostMapping("/addInternalTransaction")
    @ApiOperation(value = "Add transaction", tags = "Basic operations")
    ResponseEntity<Response> addInternalTransaction(@RequestBody TransactionRequestInternal request);


    @PostMapping("/importTransactions")
    UploadFileResponse importTransactions(Long sessionId, @RequestParam("file") MultipartFile file);

    @PostMapping("/exportTransactions/{sessionId}")
    @ResponseBody
    ResponseEntity<byte[]> exportTransactions(@PathVariable Long sessionId, String date);
}