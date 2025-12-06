package edu.bsu.cashstorage.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CurrencyDTO {
    private String id;
    private String displayName;
    private String symbol;
}