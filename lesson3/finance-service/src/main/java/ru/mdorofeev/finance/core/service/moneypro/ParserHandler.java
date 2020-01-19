package ru.mdorofeev.finance.core.service.moneypro;

import ru.mdorofeev.finance.core.exception.ServiceException;

public interface ParserHandler {
    void process(MoneyProData data) throws MoneyProImportException, ServiceException;
}
