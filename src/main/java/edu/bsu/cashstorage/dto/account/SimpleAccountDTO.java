package edu.bsu.cashstorage.dto.account;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class SimpleAccountDTO {
    private UUID id;
    private String name;
}
