package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.entity.config.Currency;
import edu.bsu.cashstorage.repository.config.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public List<Currency> searchCurrencies(@RequestParam String search) {
        return currencyRepository.findCurrenciesByDisplayNameContainingIgnoreCase(search);
    }
}
