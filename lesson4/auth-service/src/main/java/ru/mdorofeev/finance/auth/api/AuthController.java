package ru.mdorofeev.finance.auth.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mdorofeev.finance.auth.api.model.common.Session;
import ru.mdorofeev.finance.auth.api.model.request.UserData;
import ru.mdorofeev.finance.common.api.model.response.BooleanResponse;
import ru.mdorofeev.finance.common.api.model.response.ErrorResponse;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;

@RestController
@RequestMapping(name = "finance controller", produces = "application/json", path = "auth")
@Api(value = "Finance app auth REST API", tags = "User management")
public interface AuthController {

    //TODO: P2: BooleanResponse => Boolean ?
    @PostMapping("/createUser")
    @ApiOperation(value = "Create user", tags = "User management")
    ResponseEntity<LongResponse> createUser(@RequestBody UserData body);

    @PostMapping("/checkUser")
    @ApiOperation(value = "Check user", tags = "User management")
    ResponseEntity<BooleanResponse> checkUser(@RequestBody UserData body);

    @PostMapping("/createSession")
    @ApiOperation(value = "Create session for user", tags = "User management")
    ResponseEntity<Session> createSession(@RequestBody UserData body);

    //TODO: P2: move internal service: don't need return system internal ids
    @GetMapping("/getUserBySession/{sessionId}")
    @ApiOperation(value = "Get userId by session", tags = "User management")
    ResponseEntity<LongResponse> getUserBySession(@PathVariable("sessionId") Long sessionId);

    @GetMapping("/checkUserId/{userId}")
    @ApiOperation(value = "Check user id in db", tags = "User management")
    ResponseEntity<BooleanResponse> checkUserId(@PathVariable("userId") Long userId);
}