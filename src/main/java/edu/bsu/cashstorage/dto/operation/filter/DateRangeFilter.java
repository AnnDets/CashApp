package edu.bsu.cashstorage.dto.operation.filter;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
public class DateRangeFilter {
    private ZonedDateTime from;
    private ZonedDateTime to;
}
