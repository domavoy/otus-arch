package ru.mdorofeev.finance.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.mdorofeev.finance.api.common.Processor;
import ru.mdorofeev.finance.api.model.common.Response;
import ru.mdorofeev.finance.api.model.common.Session;
import ru.mdorofeev.finance.api.model.request.UserData;
import ru.mdorofeev.finance.api.model.response.BooleanResponse;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.service.AuthService;

import javax.transaction.Transactional;


@Service
public class AuthControllerImpl implements AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthControllerImpl.class);

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<Response> createUser(@RequestBody UserData body) {
        return Processor.wrapExceptions(() -> {
            authService.createUser(body.getLogin(), body.getPasssword());
            return new ResponseEntity<>(new Response(), HttpStatus.OK);
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
    @Transactional
    public ResponseEntity<Session> createSession(@RequestBody UserData body) {
        return Processor.wrapExceptions(() -> {
            User user = authService.findUser(body.getLogin(), body.getPasssword());
            if (user != null) {
                authService.deactivateSession(user);
                ru.mdorofeev.finance.persistence.Session session = authService.createSession(user);
                return new ResponseEntity<>(new Session(session.getSessionId().toString()), HttpStatus.OK);
            } else {
                throw new ServiceException("USER_NOT_FOUND");
            }
        });
    }
}