package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.mapper.AccountMapper;
import edu.bsu.cashstorage.repository.AccountRepository;
import edu.bsu.cashstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper mapper;

    @Transactional(readOnly = true)
    public List<ListAccountDTO> getAccountList(UUID userId) {
        return mapper.toListDTO(accountRepository.findByUserId(userId));
    }

    @Transactional(readOnly = true)
    public AccountDTO getAccount(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public void deleteAccount(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }

    @Transactional
    public SimpleAccountDTO createAccount(UUID userId, AccountInputDTO account) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account entity = mapper.toEntity(account, userId);
        return mapper.toSimpleDTO(accountRepository.save(entity));
    }

    public SimpleAccountDTO updateAccount(UUID userId, UUID accountId, AccountInputDTO accountInputDTO) {
        Account accountFromDB = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        mapper.patchAccount(mapper.toEntity(accountInputDTO, userId), accountFromDB);

        return mapper.toSimpleDTO(accountRepository.save(accountFromDB));
    }
}