package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.mapper.AccountMapper;
import edu.bsu.cashstorage.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_ACCOUNTS)
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping
    public List<ListAccountDTO> getAccountList(@RequestParam(APIs.Params.USER_ID) UUID userId) {
        return accountMapper.toListDTO(accountService.getAccountList(userId));
    }

    @GetMapping(APIs.Server.ID_PATH)
    public AccountDTO getAccount(@PathVariable(APIs.Params.ID) UUID accountId) {
        return accountMapper.toDTO(accountService.getAccount(accountId));
    }

    @DeleteMapping(APIs.Server.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable(APIs.Params.ID) UUID accountId) {
        accountService.deleteAccount(accountId);
    }

    @PatchMapping(APIs.Server.ID_PATH)
    public SimpleAccountDTO updateAccount(@PathVariable(APIs.Params.ID) UUID accountId,
                                          @RequestParam(APIs.Params.USER_ID) UUID userId,
                                          @RequestBody AccountInputDTO accountInputDTO) {
        return accountMapper.toSimpleDTO(accountService.updateAccount(accountId, accountMapper.toEntity(accountInputDTO, userId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleAccountDTO createAccount(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                          @RequestBody AccountInputDTO accountInputDTO) {
        return accountMapper.toSimpleDTO(accountService.createAccount(userId, accountMapper.toEntity(accountInputDTO, userId)));
    }
}
