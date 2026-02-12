package edu.bsu.cashstorage.dto.category;

import edu.bsu.cashstorage.dto.config.ColorDTO;
import edu.bsu.cashstorage.dto.config.IconDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ListCategoryDTO {
    private UUID id;
    private String name;
    private IconDTO icon;
    private ColorDTO color;
    private Boolean forIncome;
    private Boolean forOutcome;
}
