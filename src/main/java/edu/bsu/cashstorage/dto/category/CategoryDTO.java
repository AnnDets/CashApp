package edu.bsu.cashstorage.dto.category;

import edu.bsu.cashstorage.dto.config.IconDTO;
import edu.bsu.cashstorage.dto.user.UserDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class CategoryDTO {
    private UUID id;
    private String name;
    private UserDTO author;
    private Boolean forIncome;
    private Boolean forOutcome;
    private Boolean mandatoryOutcome;
    private IconDTO icon;
}