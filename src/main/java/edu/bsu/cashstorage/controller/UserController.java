package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.user.PatchUserDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
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
public class UserController {
    @GetMapping(APIs.Server.USERS_PROFILE)
    public UserDTO getDetails(@RequestParam("userId") UUID userId) {
        return null;
    }

    @PatchMapping(APIs.Server.USERS_PROFILE)
    public UserDTO updateProfile(@RequestParam("userId") UUID userId, @RequestBody PatchUserDTO patchUserDTO) {
        return null;
    }

    @DeleteMapping(APIs.Server.USERS_PROFILE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAccount(@RequestParam("userId") UUID userId) {
    }
}
