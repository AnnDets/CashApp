package edu.bsu.cashstorage.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class AuthResponseDTO {
    private final String tokenType = "Bearer";

    private UUID userId;
    private String accessToken;
    private Integer expiresIn;
}
