package edu.bsu.cashstorage.dto.place;

import edu.bsu.cashstorage.dto.user.UserDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PlaceDTO {
    private UUID id;
    private String description;
    private UserDTO author;
}