package edu.bsu.cashstorage.repository.config;

import edu.bsu.cashstorage.entity.config.Icon;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface IconRepository extends ListCrudRepository<Icon, UUID> {
}