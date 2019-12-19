package ru.mdorofeev.finance.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json", path = "message")
@Api(value = "Send SMS")
public interface SmsController {

    @GetMapping("/sendSms")
    @ApiOperation(value = "Send SMS", tags = "Sender")
    ResponseEntity<Boolean> sendSms(@RequestBody String phone, String message);
}