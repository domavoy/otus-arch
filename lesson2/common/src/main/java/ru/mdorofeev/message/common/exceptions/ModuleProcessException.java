package ru.mdorofeev.message.common.exceptions;

public class ModuleProcessException extends RuntimeException {

    public ModuleProcessException(String message) {
        super(message);
    }

    public ModuleProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
