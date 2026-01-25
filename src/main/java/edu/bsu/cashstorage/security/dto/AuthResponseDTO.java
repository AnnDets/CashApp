package edu.bsu.cashstorage.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponseDTO {
    private final String tokenType = "Bearer";

    private String accessToken;
    private Integer expiresIn;
}
