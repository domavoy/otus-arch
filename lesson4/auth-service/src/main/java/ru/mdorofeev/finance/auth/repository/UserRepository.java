package ru.mdorofeev.finance.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.auth.persistence.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password);

    @Query("select u from User u, Session s where u.id = s.user.id and s.status = 1 and s.sessionId = :sessionId")
    User findByActiveSession(@Param("sessionId") Long sessionId);
}

