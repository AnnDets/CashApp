package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.repository.AccountRepository;
import edu.bsu.cashstorage.repository.UserRepository;
import edu.bsu.cashstorage.repository.config.BankRepository;
import edu.bsu.cashstorage.repository.config.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional(readOnly = true)
    public List<Account> getAccountList(UUID userId) {
        return accountRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Account getAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    @Transactional
    public Account updateAccount(UUID accountId, Account newAccount) {
        Account accountFromDB = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return accountRepository.save(accountFromDB);
    }

    @Transactional
    public Account createAccount(UUID userId, Account account) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.save(account);
    }
}