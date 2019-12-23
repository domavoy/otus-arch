package ru.mdorofeev.sender.central.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = "application/json")
@Api(value = "Email sender")
public interface EmailController {

    @GetMapping("/sendEmail")
    @ApiOperation(value = "Send Email", tags = "Email sender")
    ResponseEntity<Response> sendEmail(@RequestBody Request request);

    @GetMapping("/getStatus")
    @ApiOperation(value = "Get email status", tags = "Email sender")
    ResponseEntity<String> getStatus(String uuid);
}