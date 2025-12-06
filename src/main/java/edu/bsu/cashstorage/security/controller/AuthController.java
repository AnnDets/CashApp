package edu.bsu.cashstorage.security.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.security.dto.AuthRequestDTO;
import edu.bsu.cashstorage.security.dto.ChangePasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(APIs.API_V1_AUTH)
public class AuthController {
    @PostMapping(APIs.AUTH_LOGIN)
    public UserDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        return null;
    }

    @PostMapping(APIs.AUTH_REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody AuthRequestDTO authRequestDTO) {
        return null;
    }

    @PatchMapping
    public UserDTO changePassword(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                  @RequestBody ChangePasswordDTO changePasswordDTO) {
        return null;
    }
}
