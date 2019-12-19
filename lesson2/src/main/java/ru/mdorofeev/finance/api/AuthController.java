package ru.mdorofeev.finance.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json", path = "message")
@Api(value = "Messages", tags = "Message management")
public interface AuthController {

    @GetMapping("/sendSms")
    @ApiOperation(value = "Send SMS", tags = "Sender")
    ResponseEntity<Boolean> sendSms(@RequestBody String phone, String message);

    @GetMapping("/sendSms")
    @ApiOperation(value = "Send Email", tags = "Sender")
    ResponseEntity<Boolean> sendEmail(@RequestBody String email, String title, String message);

    @GetMapping("/sendPush")
    @ApiOperation(value = "Send PUSH notification", tags = "Sender")
    ResponseEntity<Boolean> sendPush(@RequestBody String phone, String message);
}