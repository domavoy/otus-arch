package ru.mdorofeev.finance.budget.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mdorofeev.finance.budget.persistence.Budget;
import ru.mdorofeev.finance.budget.repository.BudgetRepository;
import ru.mdorofeev.finance.common.exception.ServiceException;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    public Long addBudget(Long userId, Long categoryId, Double amount){
        Budget budget = new Budget();
        budget.setUserId(userId);
        budget.setCategoryId(categoryId);
        budget.setAmount(amount);

        return budgetRepository.save(budget).getId();
    }

    public void updateBudget(Long budgetId, Long categoryId, Double amount) throws ServiceException {

        Optional<Budget> optionalBudget = budgetRepository.findById(budgetId);
        if(optionalBudget.isPresent()){
            Budget budget = optionalBudget.get();
            budget.setAmount(amount);
            budget.setCategoryId(categoryId);
            budgetRepository.save(budget);
        } else {
            throw new ServiceException("BUDGET_NOT_FOUND");
        }
    }

    public List<Budget> findByUserId(Long id){
        return budgetRepository.findAllByUserIdOrderById(id);
    }
}
