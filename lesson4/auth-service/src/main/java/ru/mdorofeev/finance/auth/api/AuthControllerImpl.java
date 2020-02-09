package ru.mdorofeev.finance.auth.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mdorofeev.finance.auth.api.model.common.Session;
import ru.mdorofeev.finance.auth.api.model.request.UserData;
import ru.mdorofeev.finance.common.api.Processor;
import ru.mdorofeev.finance.common.api.model.response.BooleanResponse;
import ru.mdorofeev.finance.auth.persistence.User;
import ru.mdorofeev.finance.auth.service.AuthService;
import ru.mdorofeev.finance.common.api.model.response.LongResponse;
import ru.mdorofeev.finance.common.exception.ServiceException;


@Service
public class AuthControllerImpl implements AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthControllerImpl.class);

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<LongResponse> createUser(@RequestBody UserData body) {
        return Processor.wrapExceptions(() -> {
            User user = authService.createUser(body.getLogin(), body.getPasssword());
            return new ResponseEntity<>(new LongResponse(user.getId()), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<BooleanResponse> checkUser(@RequestBody UserData body) {
        return Processor.wrapExceptions(() -> {
            User value = authService.findUser(body.getLogin(), body.getPasssword());
            return new ResponseEntity<>(new BooleanResponse(null, value != null), HttpStatus.OK);
        });
    }

    @Override
    public ResponseEntity<Session> createSession(@RequestBody UserData body) {
        return Processor.wrapExceptions(() -> {
            User user = authService.findUser(body.getLogin(), body.getPasssword());
            if (user != null) {
                authService.deactivateSession(user);
                ru.mdorofeev.finance.auth.persistence.Session session = authService.createSession(user);
                return new ResponseEntity<>(new Session(session.getSessionId().toString()), HttpStatus.OK);
            } else {
                throw new ServiceException("USER_NOT_FOUND");
            }
        });
    }

    @Override
    public ResponseEntity<LongResponse> getUserBySession(Long sessionId) {
        return Processor.wrapExceptions(() -> {
            User user = authService.findBySession(sessionId);
            if (user != null) {
                return new ResponseEntity<>(new LongResponse(null, user.getId()), HttpStatus.OK);
            } else {
                throw new ServiceException("USER_NOT_FOUND");
            }
        });
    }

    @Override
    public ResponseEntity<BooleanResponse> checkUserId(Long userId) {
        return Processor.wrapExceptions(() -> {
            User value = authService.getUserById(userId);
            return new ResponseEntity<>(new BooleanResponse(null, value != null), HttpStatus.OK);
        });
    }
}