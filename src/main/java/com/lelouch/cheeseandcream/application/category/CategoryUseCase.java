package com.lelouch.cheeseandcream.application.category;

import java.util.List;

public interface CategoryUseCase {

    void createCategory(String name);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    void updateCategory(Long id, String name);
    void deleteCategory(Long id);
}
