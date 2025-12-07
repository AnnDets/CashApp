package edu.bsu.cashstorage.dto.operation;

import edu.bsu.cashstorage.dto.account.SimpleAccountDTO;
import edu.bsu.cashstorage.dto.category.SimpleCategoryDTO;
import edu.bsu.cashstorage.dto.place.SimplePlaceDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ListOperationDTO {
    private UUID id;
    private SimpleCategoryDTO category;
    private SimpleAccountDTO accountOutcome;
    private SimpleAccountDTO accountIncome;
    private ZonedDateTime operationDate;
    private String description;
    private SimplePlaceDTO place;
    private String income;
    private String outcome;
    private Instant created;
    private Instant updated;
}
