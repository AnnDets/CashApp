package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.user.PatchUserDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.dto.user.UserRegistrationRequest;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.mapper.UserMapper;
import edu.bsu.cashstorage.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIs.Server.API_V1_USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(APIs.Server.AUTH_REGISTER)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping(APIs.Server.USERS_PROFILE)
    public UserDTO getDetails() {
        User currentUser = userService.getCurrentUser();
        return userMapper.toDTO(currentUser);
    }

    @PatchMapping(APIs.Server.USERS_PROFILE)
    public UserDTO updateProfile(@RequestBody PatchUserDTO patchUserDTO) {
        User currentUser = userService.getCurrentUser();
        return userService.updateProfile(currentUser.getId(), patchUserDTO);
    }

    @DeleteMapping(APIs.Server.USERS_PROFILE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAccount() {
        User currentUser = userService.getCurrentUser();
        userService.deleteAccount(currentUser.getId());
    }
}
