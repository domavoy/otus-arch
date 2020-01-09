package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mdorofeev.finance.core.persistence.Session;
import ru.mdorofeev.finance.core.persistence.User;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Modifying
    @Query("update Session s set s.status = :newStatus where s.user = :user")
    void updateSessions(@Param("user") User user, @Param("newStatus") Integer newStatus);

    @Modifying
    @Query("update Session s set s.status = :status where s.user = :user and s.sessionId = :sessionId")
    void updateSessionStatus(@Param("user") User user, @Param("sessionId") Long sessionId, @Param("status") Integer status);
}
