package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Currency;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface CurrencyRepository extends ListCrudRepository<Currency, UUID> {
    List<Currency> findCurrenciesByDisplayNameContainingIgnoreCase(String displayName);
}
