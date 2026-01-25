package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.user.PatchUserDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(APIs.Server.API_V1_USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(APIs.Server.USERS_PROFILE)
    public UserDTO getDetails(@RequestParam("userId") UUID userId) {
        return userService.getUserDetails(userId);
    }

    @PatchMapping(APIs.Server.USERS_PROFILE)
    public UserDTO updateProfile(@RequestParam("userId") UUID userId,
                                 @RequestBody PatchUserDTO patchUserDTO) {
        return userService.updateProfile(userId, patchUserDTO);
    }

    @DeleteMapping(APIs.Server.USERS_PROFILE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAccount(@RequestParam("userId") UUID userId) {
        userService.deleteAccount(userId);
    }
}
