package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.entity.config.Color;
import edu.bsu.cashstorage.repository.config.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}
