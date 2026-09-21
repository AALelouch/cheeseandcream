package com.lelouch.cheeseandcream.application.category;

import com.lelouch.cheeseandcream.application.category.command.SaveCategoryCommand;
import com.lelouch.cheeseandcream.application.category.dto.CategoryResponse;
import com.lelouch.cheeseandcream.application.category.query.CategoryNameExistsQuery;
import com.lelouch.cheeseandcream.application.category.query.FindAllCategoriesQuery;
import com.lelouch.cheeseandcream.application.category.query.FindCategoryByIdQuery;
import com.lelouch.cheeseandcream.domain.Category;
import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryInteractor implements CategoryUseCase {

    private final SaveCategoryCommand saveCategoryCommand;
    private final FindCategoryByIdQuery findCategoryByIdQuery;
    private final FindAllCategoriesQuery findAllCategoriesQuery;
    private final CategoryNameExistsQuery categoryNameExistsQuery;
    private final CategoryOutputPort categoryOutputPort;

    public CategoryInteractor(SaveCategoryCommand saveCategoryCommand, FindCategoryByIdQuery findCategoryByIdQuery,
            FindAllCategoriesQuery findAllCategoriesQuery, CategoryNameExistsQuery categoryNameExistsQuery,
            CategoryOutputPort categoryOutputPort) {
        this.saveCategoryCommand = saveCategoryCommand;
        this.findCategoryByIdQuery = findCategoryByIdQuery;
        this.findAllCategoriesQuery = findAllCategoriesQuery;
        this.categoryNameExistsQuery = categoryNameExistsQuery;
        this.categoryOutputPort = categoryOutputPort;
    }

    @Override
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public void createCategory(String name) {
        ValidatorUtils.validateData(() -> categoryNameExistsQuery.exists(name, null), "CategoryEntity with the same name already exists");
        saveCategoryCommand.save(Category.create(name));
    }

    @Override
    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse getCategoryById(Long id) {
        return categoryOutputPort.mapToResponse(findActiveCategory(id));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryOutputPort.mapToResponse(findAllCategoriesQuery.findAll());
    }

    @Override
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public void updateCategory(Long id, String name) {
        ValidatorUtils.validateData(() -> categoryNameExistsQuery.exists(name, id), "CategoryEntity with the same name already exists");
        saveCategoryCommand.save(findActiveCategory(id).rename(name));
    }

    @Override
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {
        saveCategoryCommand.save(findActiveCategory(id).deactivate());
    }

    private Category findActiveCategory(Long id) {
        return findCategoryByIdQuery.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
    }
}
