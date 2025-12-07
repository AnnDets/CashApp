package edu.bsu.cashstorage.security.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.mapper.UserMapper;
import edu.bsu.cashstorage.security.dto.AuthRequestDTO;
import edu.bsu.cashstorage.security.dto.ChangePasswordDTO;
import edu.bsu.cashstorage.security.dto.RegisterRequestDTO;
import edu.bsu.cashstorage.security.service.AuthService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_AUTH)
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping(APIs.Server.AUTH_LOGIN)
    public UserDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        return userMapper.toDTO(authService.login(userMapper.toEntity(authRequestDTO)));
    }

    @PostMapping(APIs.Server.AUTH_REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return userMapper.toDTO(authService.register(userMapper.toEntity(registerRequestDTO)));
    }

    @PatchMapping
    public UserDTO changePassword(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                  @RequestBody ChangePasswordDTO changePasswordDTO) {
        return userMapper.toDTO(authService.changePassword(userId, changePasswordDTO));
    }
}
