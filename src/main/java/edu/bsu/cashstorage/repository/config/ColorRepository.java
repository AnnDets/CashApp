package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Color;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ColorRepository extends CrudRepository<Color, UUID> {
}
