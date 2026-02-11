package edu.bsu.cashstorage.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cashstorage.keycloak.admin")
public class KeycloakAdminProperties {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String username;
    private String password;
    private String targetRealm;
}
