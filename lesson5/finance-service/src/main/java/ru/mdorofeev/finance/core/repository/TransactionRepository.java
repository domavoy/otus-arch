package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.core.persistence.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.userId = :userId and t.date >= :date order by t.date asc")
    List<Transaction> findByUserId(@Param("userId") Long userId, @Param("date") Date date);
}

