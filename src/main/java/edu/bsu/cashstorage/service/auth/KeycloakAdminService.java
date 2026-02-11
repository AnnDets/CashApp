package edu.bsu.cashstorage.service.auth;

import edu.bsu.cashstorage.dto.user.UserRegistrationRequest;

public interface KeycloakAdminService {
    String createUser(UserRegistrationRequest userRegistrationRequest);
}
