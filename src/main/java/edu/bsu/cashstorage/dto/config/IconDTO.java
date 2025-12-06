package edu.bsu.cashstorage.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class IconDTO {
    private UUID id;
    private String data;
}