package edu.bsu.cashstorage.mapper;

import edu.bsu.cashstorage.dto.category.CategoryDTO;
import edu.bsu.cashstorage.dto.category.CategoryInputDTO;
import edu.bsu.cashstorage.dto.category.ListCategoryDTO;
import edu.bsu.cashstorage.dto.category.SimpleCategoryDTO;
import edu.bsu.cashstorage.entity.Category;
import edu.bsu.cashstorage.mapper.config.IconMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {IconMapper.class, UserMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    List<ListCategoryDTO> toListDTO(List<Category> category);

    CategoryDTO toDTO(Category category);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "author.id", source = "userId"),
            @Mapping(target = "icon.id", source = "inputDTO.iconId"),
            @Mapping(target = "color.id", source = "inputDTO.colorId")
    })
    Category toEntity(CategoryInputDTO inputDTO, UUID userId);

    SimpleCategoryDTO toSimpleDTO(Category category);
}
