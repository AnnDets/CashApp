package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.dto.config.CurrencyDTO;
import edu.bsu.cashstorage.mapper.config.CurrencyMapper;
import edu.bsu.cashstorage.repository.config.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDTO> getCurrencies() {
        return currencyMapper.toDto(currencyRepository.findAll());
    }

    public List<CurrencyDTO> searchCurrencies(@RequestParam String search) {
        return currencyMapper.toDto(currencyRepository.findCurrenciesByDisplayNameContainingIgnoreCase(search));
    }
}
