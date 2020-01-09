package ru.mdorofeev.finance.core.parser;

import ru.mdorofeev.finance.core.exception.ServiceException;

public interface ParserHandler {
    void process(MoneyProData data) throws MoneyProImportException, ServiceException;
}
