package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.bank.BankDTO;
import edu.bsu.cashstorage.service.config.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_BANKS)
public class BankController {
    private final BankService bankService;

    @GetMapping
    public List<BankDTO> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping(APIs.Server.SEARCH_PATH)
    public List<BankDTO> searchBanks(@RequestParam String search) {
        return bankService.searchBanks(search);
    }
}
