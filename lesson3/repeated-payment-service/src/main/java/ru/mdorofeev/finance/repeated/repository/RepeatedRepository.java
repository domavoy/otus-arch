package ru.mdorofeev.finance.repeated.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;

import java.util.Date;
import java.util.List;

public interface RepeatedRepository extends JpaRepository<RepeatedPayment, Long> {

    //TODO: P2: search using date: more complex or store all dates in database for light select
    @Query("select u from RepeatedPayment u order by u.id asc")
    List<RepeatedPayment> findAllByDate(@Param("startDate") Date start);

}