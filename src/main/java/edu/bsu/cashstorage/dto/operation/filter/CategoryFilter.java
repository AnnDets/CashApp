package edu.bsu.cashstorage.dto.operation.filter;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
class CategoryFilter {
    private List<UUID> categoryIds;
    private Boolean include;
}
