package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.dto.config.ColorDTO;
import edu.bsu.cashstorage.mapper.config.ColorMapper;
import edu.bsu.cashstorage.repository.config.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public List<ColorDTO> getAllColors() {
        return colorMapper.toDto(colorRepository.findAll());
    }
}
