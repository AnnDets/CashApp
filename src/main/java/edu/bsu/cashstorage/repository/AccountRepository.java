package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.entity.enums.AccountType;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends ListCrudRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);

    List<Account> findByUserIdAndArchiveAccountFalse(UUID userId);

    List<Account> findByUserIdAndDefaultAccountTrue(UUID userId);

    List<Account> findByUserIdAndIncludeInTotalBalanceTrue(UUID userId);

    List<Account> findByUserIdAndType(UUID userId, AccountType type);
}