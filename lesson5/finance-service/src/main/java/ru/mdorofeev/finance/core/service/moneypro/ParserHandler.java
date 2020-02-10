package ru.mdorofeev.finance.core.service.moneypro;

import ru.mdorofeev.finance.common.exception.ServiceException;

public interface ParserHandler {
    void process(MoneyProData data) throws MoneyProImportException, ServiceException;
}
