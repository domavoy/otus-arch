package ru.mdorofeev.finance.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.api.model.response.ErrorResponse;
import ru.mdorofeev.finance.common.exception.ServiceException;

public class Processor {

    private Processor() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Response> ResponseEntity<T> wrapExceptions(Process<T> process) {
        try {
            return process.process();
        } catch (ServiceException e) {
            ErrorResponse error = new ErrorResponse();
            error.setCode(e.getCode());
            error.setMessage(e.getMessage());

            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(200));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.setCode("EXCEPTION");
            error.setMessage(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(new Response(error), HttpStatus.resolve(500));
        }
    }
}
