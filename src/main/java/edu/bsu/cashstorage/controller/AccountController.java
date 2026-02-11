package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.account.AccountDTO;
import edu.bsu.cashstorage.dto.account.AccountInputDTO;
import edu.bsu.cashstorage.dto.account.ListAccountDTO;
import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.service.AccountService;
import edu.bsu.cashstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_ACCOUNTS)
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping
    public List<ListAccountDTO> getAccountList() {
        User user = userService.getCurrentUser();
        return accountService.getAccountList(user.getId());
    }

    @GetMapping(APIs.Server.ID_PATH)
    public AccountDTO getAccount(@PathVariable(APIs.Params.ID) UUID accountId) {
        return accountService.getAccount(accountId);
    }

    @DeleteMapping(APIs.Server.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable(APIs.Params.ID) UUID accountId) {
        accountService.deleteAccount(accountId);
    }

    @PatchMapping(APIs.Server.ID_PATH)
    public SimpleAccountDTO updateAccount(@PathVariable(APIs.Params.ID) UUID accountId,
                                          @RequestBody AccountInputDTO accountInputDTO) {
        User user = userService.getCurrentUser();
        return accountService.updateAccount(user.getId(), accountId, accountInputDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleAccountDTO createAccount(@RequestBody AccountInputDTO accountInputDTO) {
        User user = userService.getCurrentUser();
        return accountService.createAccount(user.getId(), accountInputDTO);
    }
}
