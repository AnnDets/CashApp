package edu.bsu.cashstorage.dto.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ColorDTO {
    private String name;
    private Short red;
    private Short green;
    private Short blue;

    public String getHex() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}