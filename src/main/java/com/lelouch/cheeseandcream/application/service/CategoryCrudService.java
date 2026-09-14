package com.lelouch.cheeseandcream.application.service;

import com.lelouch.cheeseandcream.application.model.product.CategoryResponse;
import java.util.List;

public interface CategoryCrudService {

    void createCategory(String name);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    void updateCategory(Long id, String name);
    void deleteCategory(Long id);

}
