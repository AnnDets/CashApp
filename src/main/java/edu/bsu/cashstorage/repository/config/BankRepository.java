package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Bank;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface BankRepository extends ListCrudRepository<Bank, UUID> {
    List<Bank> findBankByDisplayNameContainingIgnoreCase(String displayName);
}
