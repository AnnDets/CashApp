package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.entity.enums.AccountType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends CrudRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);

    List<Account> findByUserIdAndArchiveAccountFalse(UUID userId);

    List<Account> findByUserIdAndDefaultAccountTrue(UUID userId);

    List<Account> findByUserIdAndIncludeInTotalBalanceTrue(UUID userId);

    List<Account> findByUserIdAndType(UUID userId, AccountType type);
}