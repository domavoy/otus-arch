package ru.mdorofeev.finance.auth.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public final class User {

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private long id;

    private String login;

    private String password;
}
