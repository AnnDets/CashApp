package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.config.IdDTO;
import edu.bsu.cashstorage.dto.place.SimplePlaceDTO;
import edu.bsu.cashstorage.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PlaceMapper {
    SimplePlaceDTO toSimpleDTO(Place place);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author.id", source = "userId")
    Place toEntity(SimplePlaceDTO simplePlaceDTO, UUID userId);

    List<SimplePlaceDTO> toSimpleDTO(List<Place> places);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "description", source = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(Place update, @MappingTarget Place fromDB);

    @Mapping(target = "description", ignore = true)
    @Mapping(target = "author", ignore = true)
    Place toEntity(IdDTO dto);

    @Named("placeSimpleSet")
    Place simpleSet(Place place);
}
