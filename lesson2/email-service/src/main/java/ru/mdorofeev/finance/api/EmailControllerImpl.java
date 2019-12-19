package ru.mdorofeev.finance.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class EmailControllerImpl implements EmailController {

    @Override
    public ResponseEntity<Response> sendEmail( Request request) {
        return new ResponseEntity<>(new Response("123e4567-e89b-12d3-a456-426655440000"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getStatus(String uuid) {
        return new ResponseEntity<String>("IN_PROGRESS", HttpStatus.OK);
    }
}