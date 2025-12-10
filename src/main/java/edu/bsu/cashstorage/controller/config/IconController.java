package edu.bsu.cashstorage.controller.config;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.config.IconDTO;
import edu.bsu.cashstorage.service.config.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIs.Server.API_V1_ICONS)
public class IconController {
    private final IconService iconService;

    @GetMapping
    public List<IconDTO> getIcons() {
        return iconService.findAll();
    }
}
