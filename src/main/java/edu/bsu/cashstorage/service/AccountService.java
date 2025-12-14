package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.entity.Account;
import edu.bsu.cashstorage.mapper.AccountMapper;
import edu.bsu.cashstorage.repository.AccountRepository;
import edu.bsu.cashstorage.repository.OperationRepository;
import edu.bsu.cashstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final OperationRepository operationRepository;
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

        // remove Outcome Income operations and etc garbage
        operationRepository.removeByAccountIncome_IdIsNullAndAccountOutcome_IdIsNullAndUserId(account.getUser().getId());
    }

    @Transactional
    public SimpleAccountDTO createAccount(UUID userId, AccountInputDTO account) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account entity = mapper.toEntity(account, userId);

        if (Objects.nonNull(entity.getDefaultAccount()) && entity.getDefaultAccount()) {
            removeDefaultMark(userId);
        }
        return mapper.toSimpleDTO(accountRepository.save(entity));
    }

    @Transactional
    public SimpleAccountDTO updateAccount(UUID userId, UUID accountId, AccountInputDTO accountInputDTO) {
        Account accountToUpdate = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        mapper.patchAccount(mapper.toEntity(accountInputDTO, userId), accountToUpdate);

        if (Objects.nonNull(accountToUpdate.getDefaultAccount()) && accountToUpdate.getDefaultAccount()) {
            removeDefaultMark(userId);
        }
        return mapper.toSimpleDTO(accountRepository.save(accountToUpdate));
    }

    protected void removeDefaultMark(UUID userId) {
        accountRepository.setDefaultMark(userId);
    }

    boolean existsAccount(UUID accountId) {
        return accountRepository.existsById(accountId);
    }
}