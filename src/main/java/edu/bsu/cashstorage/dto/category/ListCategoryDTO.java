package edu.bsu.cashstorage.dto.category;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ListCategoryDTO {
    private UUID categoryId;
    private String name;
}
