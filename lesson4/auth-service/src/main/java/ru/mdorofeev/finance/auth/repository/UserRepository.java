package ru.mdorofeev.finance.auth.repository;

import org.apache.ibatis.annotations.Mapper;
import ru.mdorofeev.finance.auth.persistence.User;

@Mapper
public interface UserRepository {

    void createIfNotExistsTable();


    Long insert(User model);

    User findById(Long userId);

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);

    User findByActiveSession(Long sessionId);
}
