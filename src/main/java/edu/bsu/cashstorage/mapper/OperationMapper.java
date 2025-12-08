package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.operation.InputOperationDTO;
import edu.bsu.cashstorage.dto.operation.ListOperationDTO;
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
        uses = {UserMapper.class, CategoryMapper.class, PlaceMapper.class},
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
    @Mapping(target = "place", expression = "java(dto.getPlaceId() != null ? new Place().setId(dto.getPlaceId()) : null)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalSystemOperationId", ignore = true)
    @Mapping(target = "category", expression = "java(dto.getCategoryId() != null ? new Category().setId(dto.getCategoryId()) : null)")
    @Mapping(target = "accountOutcome", expression = "java(dto.getAccountOutcomeId() != null ? new Account().setId(dto.getAccountOutcomeId()) : null)")
    @Mapping(target = "accountIncome", expression = "java(dto.getAccountIncomeId() != null ? new Account().setId(dto.getAccountIncomeId()) : null)")
    Operation toEntity(UUID userId, InputOperationDTO dto);

    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "isProcessed", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "externalSystemOperationId", ignore = true)
    @Mapping(target = "category", expression = "java(updated.getCategory() != null ? updated.getCategory() : null)")
    @Mapping(target = "accountIncome", expression = "java(updated.getAccountIncome() != null ? updated.getAccountIncome() : null)")
    @Mapping(target = "accountOutcome", expression = "java(updated.getAccountOutcome() != null ? updated.getAccountOutcome() : null)")
    void patchEntity(Operation updated, @MappingTarget Operation operation);
}
