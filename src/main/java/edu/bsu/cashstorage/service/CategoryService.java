package edu.bsu.cashstorage.service;

import edu.bsu.cashstorage.dto.category.CategoryDTO;
import edu.bsu.cashstorage.dto.category.CategoryInputDTO;
import edu.bsu.cashstorage.dto.category.ListCategoryDTO;
import edu.bsu.cashstorage.dto.category.SimpleCategoryDTO;
import edu.bsu.cashstorage.entity.Category;
import edu.bsu.cashstorage.mapper.CategoryMapper;
import edu.bsu.cashstorage.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<ListCategoryDTO> getAllCategories(UUID userId) {
        return categoryMapper.toListDTO(categoryRepository.findByUsedBy_Id(userId));
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public SimpleCategoryDTO createCategory(UUID userId,
                                            CategoryInputDTO dto) {
        Category entity = categoryMapper.toEntity(dto, userId);
        entity = categoryRepository.save(entity);
        return categoryMapper.toSimpleDTO(entity);
    }

    @Transactional
    public SimpleCategoryDTO patchCategory(UUID userId, UUID categoryId,
                                           CategoryInputDTO dto) {
        Category fromDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryMapper.patchEntity(categoryMapper.toEntity(dto, userId), fromDB);
        return categoryMapper.toSimpleDTO(categoryRepository.save(fromDB));
    }
}
