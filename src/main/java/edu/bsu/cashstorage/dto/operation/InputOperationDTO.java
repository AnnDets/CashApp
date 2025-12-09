package edu.bsu.cashstorage.dto.operation;

import edu.bsu.cashstorage.dto.config.IdDTO;
import edu.bsu.cashstorage.entity.enums.OperationType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class InputOperationDTO {
    private IdDTO category;
    private IdDTO accountOutcome;
    private IdDTO accountIncome;
    private OperationType operationType;
    private ZonedDateTime operationDate;
    private String description;
    private IdDTO place;
    private String income;
    private String outcome;
}
