package ru.mdorofeev.finance.budget.persistence.dict;

import ru.mdorofeev.finance.budget.exception.ServiceException;

public enum Granularity {
    NONE, DAILY, WEEKLY, YEARLY;

    public static Granularity from(String value) throws ServiceException {
        for (Granularity type : Granularity.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        throw new ServiceException("Incorrect Granularity " + value);
    }
}
