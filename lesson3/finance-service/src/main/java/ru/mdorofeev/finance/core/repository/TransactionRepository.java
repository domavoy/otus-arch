package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.core.persistence.Transaction;
import ru.mdorofeev.finance.core.persistence.User;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select t from Transaction t, User u, Session s where t.user.id = u.id and s.user.id = u.id and s.sessionId = :sessionId and t.date >= :date order by t.date asc")
    List<Transaction> findBySessionId(@Param("sessionId") Long sessionId, @Param("date") Date date);

    @Query("select t from Transaction t where t.user = :user and t.date >= :date order by t.date asc")
    List<Transaction> findByUser(@Param("user") User user, @Param("date") Date date);
}

