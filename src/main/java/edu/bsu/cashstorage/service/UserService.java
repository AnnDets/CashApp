package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.configuration.SecurityUtils;
import edu.bsu.cashstorage.dto.user.PatchUserDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
import edu.bsu.cashstorage.dto.user.UserRegistrationRequest;
import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.mapper.UserMapper;
import edu.bsu.cashstorage.repository.UserRepository;
import edu.bsu.cashstorage.service.auth.KeycloakAdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeycloakAdminService keycloakAdminService;

    /**
     * Gets the current user from the JWT token.
     * If the user doesn't exist in the database yet, auto-provisions from JWT claims.
     */
    @Transactional
    public User getCurrentUser() {
        UUID keycloakId = SecurityUtils.getCurrentKeycloakUserId();
        return userRepository.findById(keycloakId)
                .orElseGet(() -> autoProvisionUser(keycloakId));
    }

    private User autoProvisionUser(UUID keycloakId) {
        log.info("Auto-provisioning user profile for Keycloak ID: {}", keycloakId);
        String email = SecurityUtils.getCurrentUserEmail();
        String username = SecurityUtils.getCurrentUsername();
        String firstName = SecurityUtils.getFirstName();
        String lastName = SecurityUtils.getLastName();

        User user = new User();
        user.setId(keycloakId);
        user.setEmail(email != null ? email : keycloakId.toString());
        user.setUsername(username != null ? username : (email != null ? email : keycloakId.toString()));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDetails(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public UserDTO updateProfile(UUID userId, PatchUserDTO patchUserDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        userMapper.patchEntity(userMapper.toEntity(patchUserDTO), user);

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteAccount(UUID userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Registers a new user via Keycloak Admin API and creates a local profile.
     */
    @Transactional
    public UserDTO registerUser(UserRegistrationRequest request) {
        String keycloakUserId = keycloakAdminService.createUser(request);

        User user = new User();
        user.setId(UUID.fromString(keycloakUserId));
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        User savedUser = userRepository.save(user);
        log.info("Registered new user with ID: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }
}
