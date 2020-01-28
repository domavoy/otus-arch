package ru.mdorofeev.finance.repeated.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mdorofeev.finance.repeated.persistence.RepeatedPayment;

import java.util.Date;
import java.util.List;

public interface RepeatedRepository extends JpaRepository<RepeatedPayment, Long> {

//TODO: P2: search using date
//    @Query("select u from RepeatedPayment u where u.userId = :userId and :startDate > u.start and (:endDate > u.end or u.end is null) order by u.id asc")
//    List<RepeatedPayment> findAllByUserId(@Param("userId") Long userId, @Param("startDate") Date start, @Param("endDate") Date end);

    @Query("select u from RepeatedPayment u where u.userId = :userId")
    List<RepeatedPayment> findAllByUserId(@Param("userId") Long userId);
}