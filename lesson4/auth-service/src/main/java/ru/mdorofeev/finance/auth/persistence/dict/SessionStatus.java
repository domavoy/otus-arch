package ru.mdorofeev.finance.auth.persistence.dict;

public enum SessionStatus {
    ACTIVE(1),
    INACTIVE(2);

    public int id;

    SessionStatus(int id) {
        this.id = id;
    }
}
