package ru.mdorofeev.finance.core.persistence.dict;

public enum SessionStatus {
    ACTIVE(1),
    INACTIVE(2);

    public int id;

    SessionStatus(int id) {
        this.id = id;
    }
}
