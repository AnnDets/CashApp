package edu.bsu.cashstorage.security.service;

import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.repository.UserRepository;
import edu.bsu.cashstorage.security.dto.ChangePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User login(User user) {
        User fromDB = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No user found for email = " + user.getEmail()));

        if (fromDB.getPassword().equals(user.getPassword())) {
            return fromDB;
        } else {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User changePassword(UUID userId,
                               ChangePasswordDTO changePasswordDTO) {
        User fromDB = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found for id = " + userId));

        if (fromDB.getPassword().equals(changePasswordDTO.getOldPassword())) {
            fromDB.setPassword(changePasswordDTO.getNewPassword());
        }
        userRepository.save(fromDB);
        return fromDB;
    }
}
