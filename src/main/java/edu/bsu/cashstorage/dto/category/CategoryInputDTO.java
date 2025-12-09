package edu.bsu.cashstorage.dto.category;

import edu.bsu.cashstorage.dto.config.IdDTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryInputDTO {
    private Boolean forIncome;
    private Boolean forOutcome;
    private String name;
    private Boolean mandatoryOutcome;
    private IdDTO icon;
    private IdDTO color;
}
