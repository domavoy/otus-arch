package ru.mdorofeev.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.persistence.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByName(String name);
}
