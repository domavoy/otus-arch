package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.core.persistence.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByUserIdAndTransactionTypeAndName(Long userId, Integer type, String name);

    Category findByUserIdAndName(Long userId, String name);

    List<Category> findByUserId(Long userId);
}
