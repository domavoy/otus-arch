package ru.mdorofeev.finance.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdorofeev.finance.budget.persistence.Budget;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUserIdOrderById(Long userId);
}
