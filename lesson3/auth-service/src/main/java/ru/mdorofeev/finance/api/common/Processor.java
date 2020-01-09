package ru.mdorofeev.finance.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.api.model.common.Error;
import ru.mdorofeev.finance.api.model.common.Response;
import ru.mdorofeev.finance.exception.ServiceException;
import ru.mdorofeev.finance.persistence.User;
import ru.mdorofeev.finance.service.AuthService;

public class Processor {

    private Processor() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Response> ResponseEntity<T> wrapExceptions(Process<T> process) {
        try {
            return process.process();
        } catch (ServiceException e) {
            Error error = new Error();
            error.setCode(e.getCode());
            error.setMessage(e.getMessage());

            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(500));
        } catch (Exception e) {
            Error error = new Error();
            error.setCode("EXCEPTION");
            error.setMessage(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(500));
        }
    }

    //TODO: P2: merge logic with wrapExceptions
    public static <T extends Response> ResponseEntity<T> wrapExceptionsAndAuth(AuthService service, Long sessionId, ProcessWithUser<T> process) {
        try {
            User user = service.findBySession(sessionId);
            if (user == null) {
                throw new ServiceException("SESSION_NOT_FOUND");
            }
            return process.process(user);
        } catch (ServiceException e) {
            Error error = new Error();
            error.setCode(e.getCode());
            error.setMessage(e.getMessage());

            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(500));
        } catch (Exception e) {
            Error error = new Error();
            error.setCode("EXCEPTION");
            error.setMessage(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(500));
        }
    }

}
