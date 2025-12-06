package edu.bsu.cashstorage.dto.place;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimplePlaceDTO {
    private String description;
}
