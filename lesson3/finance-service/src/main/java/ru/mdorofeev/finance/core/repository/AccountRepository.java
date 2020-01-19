package ru.mdorofeev.finance.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Currency;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByUserIdAndCurrencyAndName(Long userId, Currency currency, String name);

    public Account findByUserIdAndName(Long userId, String name);

    List<Account> findByUserId(Long userId);
}
