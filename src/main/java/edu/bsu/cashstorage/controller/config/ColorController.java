package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.ColorDTO;
import edu.bsu.cashstorage.service.config.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_COLORS)
public class ColorController {
    private final ColorService colorService;

    @GetMapping
    public List<ColorDTO> getColors() {
        return colorService.getAllColors();
    }
}
