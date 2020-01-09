package ru.mdorofeev.finance.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mdorofeev.finance.core.exception.ServiceException;
import ru.mdorofeev.finance.core.persistence.Account;
import ru.mdorofeev.finance.core.persistence.Category;
import ru.mdorofeev.finance.core.persistence.Transaction;
import ru.mdorofeev.finance.core.persistence.User;
import ru.mdorofeev.finance.core.persistence.dict.TransactionType;
import ru.mdorofeev.finance.core.repository.AccountRepository;
import ru.mdorofeev.finance.core.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class MainService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void createTransaction(User user, Date date, Account account, Category category, Double amount, String comment) throws ServiceException {
        TransactionType transactionType = TransactionType.from(category.getTransactionType());
        createTransactionNative(user, date, transactionType, account, null, category, amount, null, comment);
    }

    @Transactional
    public void moneyTransfer(User user, Date date, Account fromAccount, Account toAccount, Double amount, String comment) throws ServiceException {
        createTransactionNative(user, date, TransactionType.MONEY_TRANSFER, fromAccount, toAccount,
                null, amount, amount, comment);
    }

    public void createTransactionNative(User user, Date date, TransactionType type, Account account, Account toAccount, Category category, Double amount, Double toAmount, String comment) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setDate(date);
        transaction.setAccount(account);
        transaction.setToAccount(toAccount);
        if (toAmount != null) {
            transaction.setToAmount(BigDecimal.valueOf(toAmount));
        }
        transaction.setComment(comment);
        transaction.setTransactionType(type.id);
        transaction.setCategory(category);

        //TODO: P2: improve complicated logic, unit tests already exists
        Double amountVal;
        if (category != null && category.getTransactionType().equals(TransactionType.EXPENSE.id)) {
            amountVal = -Math.abs(amount);
        } else {
            amountVal = Math.abs(amount);
        }
        transaction.setAmount(BigDecimal.valueOf(amountVal));
        transactionRepository.save(transaction);

        if (type != null && type.id.equals(TransactionType.MONEY_TRANSFER.id)) {
            if (toAccount != null) {
                toAccount.setAmount(toAccount.getAmount() + amountVal);
                accountRepository.save(toAccount);
            }

            account.setAmount(account.getAmount() - amountVal);
            accountRepository.save(account);
        } else {
            account.setAmount(account.getAmount() + amountVal);
            accountRepository.save(account);
        }
    }

    public List<Account> getAccounts(Long sessionId) {
        return accountRepository.getBySession(sessionId);
    }

    public List<Transaction> getTransactions(Long sessionId, Date date) {
        return transactionRepository.findBySessionId(sessionId, date);
    }

    public List<Transaction> getTransactions(User user, Date date) {
        return transactionRepository.findByUser(user, date);
    }
}
