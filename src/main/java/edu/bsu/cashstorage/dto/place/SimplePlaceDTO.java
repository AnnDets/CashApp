package edu.bsu.cashstorage.dto.place;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class SimplePlaceDTO {
    private UUID id;
    private String description;
}
