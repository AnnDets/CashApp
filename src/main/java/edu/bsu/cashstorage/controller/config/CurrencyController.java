package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.CurrencyDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIs.API_V1_CURRENCIES)
public class CurrencyController {
    @GetMapping
    public List<CurrencyDTO> getCurrencies() {
        return null;
    }

    @GetMapping(APIs.Paths.SEARCH_PATH)
    public List<CurrencyDTO> searchCurrencies(@RequestParam String search) {
        return null;
    }
}
