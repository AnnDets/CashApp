package edu.bsu.cashstorage.dto.operation.filter;

import edu.bsu.cashstorage.entity.enums.OperationType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class OperationFilterDTO {
    private DateRangeFilter dateRange;
    private List<UUID> accountIds;
    private CategoryFilter categoryFilter;
    private Boolean notProcessed;
    private List<OperationType> operationTypes;
}

