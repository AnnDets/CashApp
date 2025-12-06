package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Currency;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CurrencyRepository extends CrudRepository<Currency, UUID> {
}
