package edu.bsu.cashstorage.service.auth.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bsu.cashstorage.configuration.KeycloakAdminProperties;
import edu.bsu.cashstorage.dto.user.UserRegistrationRequest;
import edu.bsu.cashstorage.service.auth.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final KeycloakAdminProperties keycloakAdminProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String createUser(UserRegistrationRequest userRegistrationRequest) {
        try {
            String adminToken = getAdminToken();
            String userId = createKeycloakUser(userRegistrationRequest, adminToken);
            setUserPassword(userId, userRegistrationRequest.getPassword(), adminToken);

            log.info("Successfully created user in Keycloak with ID: {}", userId);
            return userId;
        } catch (Exception e) {
            log.error("Failed to create user in Keycloak", e);
            throw new RuntimeException("Failed to create user in Keycloak", e);
        }
    }

    private String getAdminToken() {
        try {
            String tokenUrl = keycloakAdminProperties.getServerUrl() + "/realms/" +
                              keycloakAdminProperties.getRealm() + "/protocol/openid-connect/token";

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "password");
            formData.add("client_id", keycloakAdminProperties.getClientId());
            formData.add("username", keycloakAdminProperties.getUsername());
            formData.add("password", keycloakAdminProperties.getPassword());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode tokenResponse = objectMapper.readTree(response.getBody());
                return tokenResponse.get("access_token").asText();
            } else {
                throw new RuntimeException("Failed to get admin token: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Failed to get admin token", e);
            throw new RuntimeException("Failed to get admin token", e);
        }
    }

    private String createKeycloakUser(UserRegistrationRequest userRequest, String adminToken) {
        try {
            String createUserUrl = keycloakAdminProperties.getServerUrl() + "/admin/realms/" +
                                   keycloakAdminProperties.getTargetRealm() + "/users";

            Map<String, Object> userPayload = new HashMap<>();
            userPayload.put("enabled", true);
            userPayload.put("username", userRequest.getEmail());
            userPayload.put("emailVerified", true);
            userPayload.put("email", userRequest.getEmail());

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(adminToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(createUserUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                String locationHeader = response.getHeaders().getFirst("Location");
                if (locationHeader != null) {
                    return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
                } else {
                    throw new RuntimeException("Location header not found in create user response");
                }
            } else {
                throw new RuntimeException("Failed to create user: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Failed to create Keycloak user", e);
            throw new RuntimeException("Failed to create Keycloak user", e);
        }
    }

    private void setUserPassword(String userId, String password, String adminToken) {
        try {
            String resetPasswordUrl = keycloakAdminProperties.getServerUrl() + "/admin/realms/" +
                                      keycloakAdminProperties.getTargetRealm() + "/users/" + userId + "/reset-password";

            Map<String, Object> passwordPayload = new HashMap<>();
            passwordPayload.put("type", "password");
            passwordPayload.put("value", password);
            passwordPayload.put("temporary", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(adminToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(passwordPayload, headers);
            restTemplate.put(resetPasswordUrl, request);
        } catch (Exception e) {
            log.error("Failed to set user password", e);
            throw new RuntimeException("Failed to set user password", e);
        }
    }
}
