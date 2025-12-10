package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.dto.config.bank.BankDTO;
import edu.bsu.cashstorage.mapper.config.BankMapper;
import edu.bsu.cashstorage.repository.config.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    public List<BankDTO> getAllBanks() {
        return bankMapper.toDTO(bankRepository.findAll());
    }

    public List<BankDTO> searchBanks(@RequestParam String search) {
        return bankMapper.toDTO(bankRepository.findBankByDisplayNameContainingIgnoreCase(search));
    }
}
