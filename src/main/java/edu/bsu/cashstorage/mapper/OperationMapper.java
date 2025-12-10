package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.dto.operation.SimpleOperationDTO;
import edu.bsu.cashstorage.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, CategoryMapper.class, PlaceMapper.class, AccountMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OperationMapper {
    ListOperationDTO toListDTO(Operation operation);

    List<ListOperationDTO> toListDTO(List<Operation> operations);

    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "isProcessed", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalSystemOperationId", ignore = true)
    Operation toEntity(InputOperationDTO dto, UUID userId);

    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "isProcessed", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "externalSystemOperationId", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "categorySimpleSet")
    @Mapping(target = "accountOutcome", source = "accountOutcome", qualifiedByName = "accountSimpleSet")
    @Mapping(target = "accountIncome", source = "accountIncome", qualifiedByName = "accountSimpleSet")
    @Mapping(target = "place", source = "place", qualifiedByName = "placeSimpleSet")
    void patchEntity(Operation updated, @MappingTarget Operation operation);

    SimpleOperationDTO toSimpleDTO(Operation entity);
}
