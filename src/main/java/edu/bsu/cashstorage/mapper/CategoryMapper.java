package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.category.CategoryDTO;
import edu.bsu.cashstorage.dto.category.CategoryInputDTO;
import edu.bsu.cashstorage.dto.category.ListCategoryDTO;
import edu.bsu.cashstorage.dto.category.SimpleCategoryDTO;
import edu.bsu.cashstorage.dto.config.IdDTO;
import edu.bsu.cashstorage.entity.Category;
import edu.bsu.cashstorage.mapper.config.ColorMapper;
import edu.bsu.cashstorage.mapper.config.IconMapper;
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
        uses = {IconMapper.class, UserMapper.class, ColorMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    List<ListCategoryDTO> toListDTO(List<Category> category);

    CategoryDTO toDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author.id", source = "userId")
    Category toEntity(CategoryInputDTO inputDTO, UUID userId);

    SimpleCategoryDTO toSimpleDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    void patchEntity(Category updated, @MappingTarget Category fromDB);

    @Named("categorySimpleSet")
    Category simpleSet(Category c);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "mandatoryOutcome", ignore = true)
    @Mapping(target = "icon", ignore = true)
    @Mapping(target = "forOutcome", ignore = true)
    @Mapping(target = "forIncome", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "author", ignore = true)
    Category toEntity(IdDTO dto);
}
