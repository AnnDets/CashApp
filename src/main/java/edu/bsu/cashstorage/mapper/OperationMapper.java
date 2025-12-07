package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
import edu.bsu.cashstorage.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, CategoryMapper.class, PlaceMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OperationMapper {
    ListOperationDTO toListDTO(Operation operation);

    List<ListOperationDTO> toListDTO(List<Operation> operations);

    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "isProcessed", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "place.id", source = "placeId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalSystemOperationId", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "accountOutcome.id", source = "accountOutcomeId")
    @Mapping(target = "accountIncome.id", source = "accountIncomeId")
    Operation toEntity(InputOperationDTO dto);
}
