package ru.mdorofeev.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.persistence.Category;
import ru.mdorofeev.finance.persistence.User;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByUserAndTransactionTypeAndName(User user, Integer type, String name);

    Category findByUserAndName(User user, String name);

    @Query("select c from Category c, User u, Session s where c.user.id = u.id and u.id = s.user.id and s.sessionId = :sessionId")
    List<Category> getBySession(@Param("sessionId") Long sessionId);
}
