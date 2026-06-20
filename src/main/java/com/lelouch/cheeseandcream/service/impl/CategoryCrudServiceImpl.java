package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.ValidatorUtils;
import com.lelouch.cheeseandcream.entity.product.Category;
import com.lelouch.cheeseandcream.exception.NotFoundException;
import com.lelouch.cheeseandcream.mapper.CategoryMapper;
import com.lelouch.cheeseandcream.model.product.CategoryResponse;
import com.lelouch.cheeseandcream.repository.CategoryRepository;
import com.lelouch.cheeseandcream.service.CategoryCrudService;
import java.util.List;
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
        ValidatorUtils.validateData(() -> categoryRepository.existsByNameAndActiveIsTrue(name), "Category with the same name already exists");
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toResponse(categoryRepository.findByIdAndActiveIsTrue(id).orElseThrow(() -> new NotFoundException("Category not found")));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByActiveIsTrue().stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    public void updateCategory(Long id, String name) {
        ValidatorUtils.validateData(() -> categoryRepository.existsByNameAndActiveIsTrueAndIdNot(name, id), "Category with the same name already exists");
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(name);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setActive(false);
        categoryRepository.save(category);

    }
}
