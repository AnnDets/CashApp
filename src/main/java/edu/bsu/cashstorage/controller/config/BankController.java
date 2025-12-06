package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.bank.BankDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(APIs.API_V1_BANKS)
public class BankController {
    @GetMapping
    public List<BankDTO> getAllBanks() {
        return null;
    }

    @GetMapping(APIs.Paths.SEARCH_PATH)
    public List<BankDTO> searchBanks(@RequestParam String search) {
        return null;
    }
}
