package edu.bsu.cashstorage.dto.operation.filter;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class OperationFilterDTO {
    private DateRangeDTO dateRange;
    private List<UUID> accountIds;
    private CategoryFilter categoryFilter;
    private Boolean notProcessed;
}

