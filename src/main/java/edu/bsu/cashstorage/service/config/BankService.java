package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.entity.config.Bank;
import edu.bsu.cashstorage.repository.config.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public List<Bank> searchBanks(@RequestParam String search) {
        return bankRepository.findBankByDisplayNameContainingIgnoreCase(search);
    }
}
