package ru.mdorofeev.finance.exception;

public class ServiceException extends Exception {

    //TODO: P3: Code to enum ?
    private String code;

    public ServiceException(String message, String code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code) {
        super(code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
