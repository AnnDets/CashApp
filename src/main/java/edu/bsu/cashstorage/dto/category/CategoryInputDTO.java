package edu.bsu.cashstorage.dto.category;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class CategoryInputDTO {
    private Boolean forIncome;
    private Boolean forOutcome;
    private Boolean mandatoryOutcome;
    private UUID iconId;
    private UUID colorId;
}
