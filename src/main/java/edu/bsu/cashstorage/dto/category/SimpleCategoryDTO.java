package edu.bsu.cashstorage.dto.category;

import edu.bsu.cashstorage.dto.config.ColorDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class SimpleCategoryDTO {
    private UUID id;
    private String name;
    private ColorDTO color;
}