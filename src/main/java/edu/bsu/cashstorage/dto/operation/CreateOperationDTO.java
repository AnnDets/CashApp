package edu.bsu.cashstorage.dto.operation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class CreateOperationDTO {
    private UUID categoryId;
    private UUID accountOutcomeId;
    private UUID accountIncomeId;
    private ZonedDateTime operationDate;
    private String description;
    private UUID placeId;
    private String income;
    private String outcome;
}
