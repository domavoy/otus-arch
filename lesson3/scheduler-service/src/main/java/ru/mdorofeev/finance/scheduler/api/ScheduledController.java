package ru.mdorofeev.finance.scheduler.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.common.api.model.response.Response;

//TODO: P2: FEATURE add security check
@RestController
@RequestMapping(name = "scheduled controller", produces = "application/json", path = "scheduler")
@Api(value = "Finance app scheduled REST API", tags = "Scheduled manual execution")
public interface ScheduledController {

    @GetMapping("/executeCurrencyUpload")
    @ApiOperation(value = "Execute currency upload", tags = "Scheduled manual execution")
    ResponseEntity<Response> executeCurrencyUpload();

    @GetMapping("/executeCreateRepeatedPayment")
    @ApiOperation(value = "Execute create repeated payment", tags = "Scheduled manual execution")
    ResponseEntity<Response> executeCreateRepeatedPayment();
}