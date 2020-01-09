package ru.mdorofeev.finance.parser;

public class MoneyProImportException extends RuntimeException{

    public MoneyProImportException(String message) {
        super(message);
    }

    public MoneyProImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
