package edu.bsu.cashstorage.mapper.config;

import edu.bsu.cashstorage.dto.config.IconDTO;
import edu.bsu.cashstorage.entity.config.Icon;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface IconMapper {
    IconDTO toDTO(Icon icon);

    Icon toEntity(IconDTO iconDTO);

    List<IconDTO> toDTO(List<Icon> icons);

    List<Icon> toEntity(List<Icon> icons);
}
