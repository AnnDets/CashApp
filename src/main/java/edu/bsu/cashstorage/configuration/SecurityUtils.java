package edu.bsu.cashstorage.configuration;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Extracts the Keycloak user ID (sub claim) from the current JWT token.
     */
    public static UUID getCurrentKeycloakUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UUID.fromString(jwt.getSubject());
    }

    /**
     * Extracts the email from the current JWT token.
     */
    public static String getCurrentUserEmail() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaimAsString("email");
    }

    /**
     * Extracts the preferred_username from the current JWT token.
     */
    public static String getCurrentUsername() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaimAsString("preferred_username");
    }
}
