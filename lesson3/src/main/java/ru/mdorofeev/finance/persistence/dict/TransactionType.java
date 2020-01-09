package ru.mdorofeev.finance.persistence.dict;

import ru.mdorofeev.finance.exception.ServiceException;

//TODO: P2: only EXPENSE/INCOME
//TODO: P2: sync with db ?
public enum TransactionType {
    EXPENSE(1),
    INCOME(2),
    MONEY_TRANSFER(3),
    NEW_ACCOUNT(4);

    public Integer id;

    TransactionType(int id) {
        this.id = id;
    }

    public static TransactionType from(Integer value) throws ServiceException {
        for (TransactionType type : TransactionType.values()) {
            if (type.id.equals(value)) {
                return type;
            }
        }

        throw new ServiceException("Incorrect TransactionType " + value);
    }

    public static TransactionType fromWithNull(Integer value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.id.equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static TransactionType from(String value) throws ServiceException {
        for (TransactionType type : TransactionType.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        throw new ServiceException("Incorrect TransactionType " + value);
    }
}