package com.lelouch.cheeseandcream.infra.category.adapter;

import com.lelouch.cheeseandcream.application.category.query.CategoryNameExistsQuery;
import com.lelouch.cheeseandcream.application.category.query.FindAllCategoriesQuery;
import com.lelouch.cheeseandcream.application.category.query.FindCategoryByIdQuery;
import com.lelouch.cheeseandcream.application.category.command.SaveCategoryCommand;
import com.lelouch.cheeseandcream.domain.Category;
import com.lelouch.cheeseandcream.infra.category.CategoryJpaMapper;
import com.lelouch.cheeseandcream.infra.category.persistence.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaCategoryAdapter implements SaveCategoryCommand, FindCategoryByIdQuery,
        FindAllCategoriesQuery, CategoryNameExistsQuery {

    private final CategoryRepository categoryRepository;
    private final CategoryJpaMapper categoryJpaMapper;

    public JpaCategoryAdapter(CategoryRepository categoryRepository, CategoryJpaMapper categoryJpaMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryJpaMapper = categoryJpaMapper;
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(categoryJpaMapper.toEntity(category));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findByIdAndActiveIsTrue(id).map(categoryJpaMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllByActiveIsTrue().stream().map(categoryJpaMapper::toDomain).toList();
    }

    @Override
    public boolean exists(String name, Long excludedId) {
        return excludedId == null
                ? categoryRepository.existsByNameAndActiveIsTrue(name)
                : categoryRepository.existsByNameAndActiveIsTrueAndIdNot(name, excludedId);
    }
}
