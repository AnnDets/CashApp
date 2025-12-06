package edu.bsu.cashstorage.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ColorDTO {
    private UUID id;
    private Short red;
    private Short green;
    private Short blue;

    public String getHex() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}