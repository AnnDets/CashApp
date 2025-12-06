package edu.bsu.cashstorage.dto.config.bank;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class SimpleBankDTO {
    private UUID id;
    private String name;
}
