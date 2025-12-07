package edu.bsu.cashstorage.dto.operation;

import edu.bsu.cashstorage.entity.enums.OperationType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class InputOperationDTO {
    private UUID categoryId;
    private UUID accountOutcomeId;
    private UUID accountIncomeId;
    private OperationType operationType;
    private ZonedDateTime operationDate;
    private String description;
    private UUID placeId;
    private String income;
    private String outcome;
}
