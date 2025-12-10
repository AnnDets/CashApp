package edu.bsu.cashstorage.repository;

import edu.bsu.cashstorage.entity.Account;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends ListCrudRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);
}