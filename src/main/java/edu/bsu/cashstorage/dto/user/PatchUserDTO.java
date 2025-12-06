package edu.bsu.cashstorage.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PatchUserDTO {
    private String username;
    private String email;
}
