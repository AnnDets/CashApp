package edu.bsu.cashstorage.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class NameDTO {
    private String name;
}
