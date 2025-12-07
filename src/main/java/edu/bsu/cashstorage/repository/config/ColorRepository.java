package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Color;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface ColorRepository extends ListCrudRepository<Color, UUID> {
}
