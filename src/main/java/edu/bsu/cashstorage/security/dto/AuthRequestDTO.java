package edu.bsu.cashstorage.security.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequestDTO {
    private String email;
    private String password;
}
