package ru.mdorofeev.finance.repeated.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.repeated.api.model.response.RepeatedPaymentResponse;
import ru.mdorofeev.finance.repeated.api.model.response.Response;

import java.util.Date;

@RestController
@RequestMapping(name = "repeated payment controller", produces = "application/json", path = "repeatedPayment")
@Api(value = "Finance app repeated payment REST API", tags = {"Repeated payment management"})
//TODO: P1: userId => session as in finance service
public interface RepeatedPaymentController {

    @PostMapping("/addRepeatedPayment")
    @ApiOperation(value = "Add repeated payment", tags = "Repeated payment management")
    ResponseEntity<Response> addRepeatedPayment(@RequestBody RepeatedPaymentData body);

    @PostMapping("/updateRepeatedPayment")
    @ApiOperation(value = "Update repeated payment data", tags = "Repeated payment management")
    ResponseEntity<Response> updateRepeatedPaymentData(@RequestBody RepeatedPaymentDataUpdate body);

    @GetMapping("/getRepeatedPayments")
    @ApiOperation(value = "Get repeated data", tags = "Repeated payment management")
    ResponseEntity<RepeatedPaymentResponse> getRepeatedData(Long userId, Date start, Date end, String granularity);
}