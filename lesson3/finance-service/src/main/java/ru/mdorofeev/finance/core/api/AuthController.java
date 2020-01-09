package ru.mdorofeev.finance.core.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mdorofeev.finance.core.api.model.common.Response;
import ru.mdorofeev.finance.core.api.model.common.Session;
import ru.mdorofeev.finance.core.api.model.request.UserData;
import ru.mdorofeev.finance.core.api.model.response.BooleanResponse;

@RestController
@RequestMapping(name = "finance controller", produces = "application/json", path = "auth")
@Api(value = "Finance app auth REST API", tags = "User management")
public interface AuthController {

    @PostMapping("/createUser")
    @ApiOperation(value = "Create user", tags = "User management")
    ResponseEntity<Response> createUser(@RequestBody UserData body);

    @PostMapping("/checkUser")
    @ApiOperation(value = "Check user", tags = "User management")
    ResponseEntity<BooleanResponse> checkUser(@RequestBody UserData body);

    @PostMapping("/createSession")
    @ApiOperation(value = "Create session for user", tags = "User management")
    ResponseEntity<Session> createSession(@RequestBody UserData body);
}