package ru.mdorofeev.finance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.persistence.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select t from Transaction t, User u, Session s where t.user.id = u.id and s.user.id = u.id and s.sessionId = :sessionId and t.date >= :date order by t.date asc")
    List<Transaction> findByDate(@Param("sessionId") Long sessionId, @Param("date") Date date);
}

