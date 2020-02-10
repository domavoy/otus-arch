package ru.mdorofeev.finance.repeated.persistence.dict;

import ru.mdorofeev.finance.common.exception.ServiceException;

public enum Granularity {

    NONE(1L),
    MONTHLY(2L),
    YEARLY(3L);

    private Long id;

    Granularity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Granularity from(String value) throws ServiceException {
        for (Granularity type : Granularity.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        throw new ServiceException("Incorrect Granularity " + value);
    }
}
