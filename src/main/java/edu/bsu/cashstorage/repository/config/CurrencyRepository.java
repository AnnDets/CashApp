package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Currency;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CurrencyRepository extends ListCrudRepository<Currency, String> {
    List<Currency> findCurrenciesByDisplayNameContainingIgnoreCase(String displayName);
}
