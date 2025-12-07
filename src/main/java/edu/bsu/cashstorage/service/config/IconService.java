package edu.bsu.cashstorage.service.config;

import edu.bsu.cashstorage.entity.config.Icon;
import edu.bsu.cashstorage.repository.config.IconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IconService {
    private final IconRepository iconRepository;

    public List<Icon> findAll() {
        return iconRepository.findAll();
    }
}
