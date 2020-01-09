package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Currency;
import ru.mdorofeev.finance.core.persistence.User;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByUserAndCurrencyAndName(User user, Currency currency, String name);

    public Account findByUserAndName(User user, String name);

    @Query("select c from Account c, User u, Session s where c.user.id = u.id and u.id = s.user.id and s.sessionId = :sessionId")
    List<Account> getBySession(@Param("sessionId") Long sessionId);
}
