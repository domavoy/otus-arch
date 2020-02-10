package ru.mdorofeev.finance.auth.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.mdorofeev.finance.common.api.model.response.ErrorResponse;
import ru.mdorofeev.finance.common.api.model.response.Response;
import ru.mdorofeev.finance.common.exception.ServiceException;


public class ProcessWithUserWrapper {

    private ProcessWithUserWrapper() {
        throw new UnsupportedOperationException();
    }

    //TODO: P2: merge logic with wrapExceptions
    public static <T> ResponseEntity<T> wrapExceptionsAndAuth(AuthServiceClient authServiceClient, Long sessionId, ProcessWithUser<T> process) {
        try {
            Long userId = authServiceClient.findBySession(sessionId);
            if (userId == null) {
                throw new ServiceException("SESSION_NOT_FOUND");
            }
            return process.process(userId);
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