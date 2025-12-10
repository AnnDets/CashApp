package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.CurrencyDTO;
import edu.bsu.cashstorage.service.config.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_CURRENCIES)
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDTO> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @GetMapping(APIs.Server.SEARCH_PATH)
    public List<CurrencyDTO> searchCurrencies(@RequestParam String search) {
        return currencyService.searchCurrencies(search);
    }
}
