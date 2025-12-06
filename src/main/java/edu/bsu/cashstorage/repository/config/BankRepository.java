package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BankRepository extends CrudRepository<Bank, UUID> {
}
