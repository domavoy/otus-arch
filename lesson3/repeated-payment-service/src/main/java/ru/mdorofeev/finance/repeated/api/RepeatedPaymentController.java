package ru.mdorofeev.finance.repeated.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.repeated.api.model.request.FuturePaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.InfinitePaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentData;
import ru.mdorofeev.finance.repeated.api.model.request.RepeatedPaymentDataUpdate;
import ru.mdorofeev.finance.repeated.api.model.response.RepeatedPaymentResponse;

import java.util.Date;

@RestController
@RequestMapping(name = "repeated payment controller", produces = "application/json", path = "repeatedPayment")
@Api(value = "Finance app repeated payment REST API", tags = {"Repeated payment management"})
//TODO: P1: userId => session as in finance service and move user => session to common ?
public interface RepeatedPaymentController {

    @PostMapping("/addFuturePayment")
    @ApiOperation(value = "Add future payment", tags = "Repeated payment management")
    ResponseEntity<LongResponse> addFuturePayment(@RequestBody FuturePaymentData body);

    @PostMapping("/addInfinitePayment")
    @ApiOperation(value = "Add infinite payment", tags = "Repeated payment management")
    ResponseEntity<LongResponse> addInfinitePayment(@RequestBody InfinitePaymentData body);

    @PostMapping("/addRepeatedPayment")
    @ApiOperation(value = "Add repeated payment", tags = "Repeated payment management")
    ResponseEntity<LongResponse> addPayment(@RequestBody RepeatedPaymentData body);

    @PostMapping("/updateRepeatedPayment")
    @ApiOperation(value = "Update repeated payment data", tags = "Repeated payment management")
    ResponseEntity<Response> updatePayment(@RequestBody RepeatedPaymentDataUpdate body);

    @GetMapping("/deleteRepeatedPayment")
    @ApiOperation(value = "Delete payment data", tags = "Repeated payment management")
    ResponseEntity<Response> deletePayment(Long userId, Long paymentId);



    @GetMapping("/getForDate")
    @ApiOperation(value = "Get payments for date", tags = "Repeated payment management")
    public ResponseEntity<RepeatedPaymentResponse> getPaymentForDate(Long userId, Date date);

//    @GetMapping("/getRepeatedPayments")
//    @ApiOperation(value = "Get repeated data", tags = "Repeated payment management")
//    ResponseEntity<RepeatedPaymentResponse> getPaymentData(Long userId, Date start, Date end, String granularity);
}