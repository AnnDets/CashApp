package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.dto.config.IconDTO;
import edu.bsu.cashstorage.mapper.config.IconMapper;
import edu.bsu.cashstorage.repository.config.IconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IconService {
    private final IconRepository iconRepository;
    private final IconMapper iconMapper;

    public List<IconDTO> findAll() {
        return iconMapper.toDTO(iconRepository.findAll());
    }
}
