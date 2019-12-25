package ru.mdorofeev.message.central.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.message.central.api.dto.EmailRequest;
import ru.mdorofeev.message.central.api.dto.Response;
import ru.mdorofeev.message.central.api.dto.SmsRequest;

@RestController
@RequestMapping(produces = "application/json")
@Api(value = "Sender")
public interface CentralController {

    @PostMapping("/sendEmail")
    @ApiOperation(value = "Send Email", tags = "Sender")
    ResponseEntity<Response> sendEmail(@RequestBody EmailRequest request);

    @PostMapping("/sendSms")
    @ApiOperation(value = "Send SMS", tags = "Sender")
    ResponseEntity<Response> sendSms(@RequestBody SmsRequest request);



    @GetMapping("/getStatus")
    @ApiOperation(value = "Get email status", tags = "Sender")
    ResponseEntity<String> getStatus(String uuid);
}