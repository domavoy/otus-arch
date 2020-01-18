package ru.mdorofeev.finance.auth.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.auth.api.model.common.Response;
import ru.mdorofeev.finance.auth.exception.ServiceException;
import ru.mdorofeev.finance.auth.persistence.User;
import ru.mdorofeev.finance.auth.service.AuthService;
import ru.mdorofeev.finance.auth.api.model.common.Error;


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
}
