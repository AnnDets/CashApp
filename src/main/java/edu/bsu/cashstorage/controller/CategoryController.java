package edu.bsu.cashstorage.controller;

import edu.bsu.cashstorage.api.APIs;
import edu.bsu.cashstorage.dto.category.CategoryDTO;
import edu.bsu.cashstorage.dto.category.CategoryInputDTO;
import edu.bsu.cashstorage.dto.category.ListCategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(APIs.API_V1_CATEGORIES)
public class CategoryController {
    @GetMapping
    public List<ListCategoryDTO> getAllCategories(@RequestParam(APIs.Params.USER_ID) UUID userId) {
        return null;
    }

    @GetMapping(APIs.Paths.ID_PATH)
    public CategoryDTO getCategory(@PathVariable(APIs.Params.ID) UUID categoryId) {
        return null;
    }

    @DeleteMapping(APIs.Paths.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(APIs.Params.ID) UUID categoryId) {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDTO createCategory(@RequestParam(APIs.Params.USER_ID) UUID userId,
                                      @RequestBody CategoryInputDTO categoryDTO) {
        return null;
    }

    @PatchMapping(APIs.Paths.ID_PATH)
    public CategoryDTO patchCategory(@PathVariable(APIs.Params.ID) UUID categoryId,
                                     @RequestParam(APIs.Params.USER_ID) UUID userId,
                                     @RequestBody CategoryInputDTO categoryDTO
    ) {
        return null;
    }
}
