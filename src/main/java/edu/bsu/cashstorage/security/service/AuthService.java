package edu.bsu.cashstorage.security.service;

import edu.bsu.cashstorage.entity.User;
import edu.bsu.cashstorage.repository.UserRepository;
import edu.bsu.cashstorage.security.dto.AuthResponseDTO;
import edu.bsu.cashstorage.security.dto.ChangePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Scope()
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${cash-app.security.token-ttl-in-seconds}")
    private Integer tokenTtlInSeconds;

    public AuthResponseDTO login(User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        User found = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return getSuccessfulAuthResponse(found);
    }

    public AuthResponseDTO register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);

        return getSuccessfulAuthResponse(saved);
    }

    @Transactional
    public AuthResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)
                && authentication instanceof UsernamePasswordAuthenticationToken tokenAuth
                && Objects.nonNull(tokenAuth.getPrincipal())) {
            UserDetails user = (UserDetails) tokenAuth.getPrincipal();

            Objects.requireNonNull(user.getPassword());
            if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
                User found = userRepository.findByUsername(user.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Invalid username..."));

                found.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(found);
                return getSuccessfulAuthResponse(found);
            }
        }
        throw new IllegalArgumentException("Invalid old password...");
    }

    private AuthResponseDTO getSuccessfulAuthResponse(User saved) {
        String token = jwtService.generateToken(saved);

        return new AuthResponseDTO()
                .setUserId(saved.getId())
                .setAccessToken(token)
                .setExpiresIn(tokenTtlInSeconds * 1000);
    }
}
