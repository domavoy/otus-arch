package ru.mdorofeev.sender.central.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.sender.central.api.dto.EmailRequest;
import ru.mdorofeev.sender.central.api.dto.Response;

@RestController
@RequestMapping(produces = "application/json")
@Api(value = "Email sender")
public interface CentralController {

    @PostMapping("/sendEmail")
    @ApiOperation(value = "Send Email", tags = "Email sender")
    ResponseEntity<Response> sendEmail(@RequestBody EmailRequest request);

    @GetMapping("/getStatus")
    @ApiOperation(value = "Get email status", tags = "Email sender")
    ResponseEntity<String> getStatus(String uuid);
}