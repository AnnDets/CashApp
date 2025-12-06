package edu.bsu.cashstorage.dto.operation.filter;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Data
@Accessors(chain = true)
class DateRangeDTO {
    private ZonedDateTime from;
    private ZonedDateTime to;
}
