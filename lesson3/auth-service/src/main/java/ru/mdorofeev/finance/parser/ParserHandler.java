package ru.mdorofeev.finance.parser;

import ru.mdorofeev.finance.exception.ServiceException;

public interface ParserHandler {
    void process(MoneyProData data) throws MoneyProImportException, ServiceException;
}
