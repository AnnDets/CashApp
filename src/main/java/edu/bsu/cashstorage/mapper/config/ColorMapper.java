package edu.bsu.cashstorage.mapper.config;

import edu.bsu.cashstorage.dto.config.ColorDTO;
import edu.bsu.cashstorage.entity.config.Color;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ColorMapper {
    ColorDTO toDto(Color color);

    Color toEntity(ColorDTO dto);

    List<Color> toEntity(List<ColorDTO> dto);

    List<ColorDTO> toDto(List<Color> dto);
}
