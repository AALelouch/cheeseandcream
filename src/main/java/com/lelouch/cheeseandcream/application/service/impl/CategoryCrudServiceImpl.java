package com.lelouch.cheeseandcream.application.service.impl;

import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.infra.orm.product.CategoryEntity;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.mapper.CategoryMapper;
import com.lelouch.cheeseandcream.application.model.product.CategoryResponse;
import com.lelouch.cheeseandcream.infra.repository.CategoryRepository;
import com.lelouch.cheeseandcream.application.service.CategoryCrudService;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryCrudServiceImpl implements CategoryCrudService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryCrudServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void createCategory(String name) {
        ValidatorUtils.validateData(() -> categoryRepository.existsByNameAndActiveIsTrue(name), "CategoryEntity with the same name already exists");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryRepository.save(categoryEntity);
    }

    @Override
    @Cacheable(value = "categories", key = "#id")
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(categoryRepository.findByIdAndActiveIsTrue(id).orElseThrow(() -> new NotFoundException("CategoryEntity not found")));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByActiveIsTrue().stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    public void updateCategory(Long id, String name) {
        ValidatorUtils.validateData(() -> categoryRepository.existsByNameAndActiveIsTrueAndIdNot(name, id), "CategoryEntity with the same name already exists");
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        categoryEntity.setName(name);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("CategoryEntity not found"));
        categoryEntity.setActive(false);
        categoryRepository.save(categoryEntity);

    }
}
