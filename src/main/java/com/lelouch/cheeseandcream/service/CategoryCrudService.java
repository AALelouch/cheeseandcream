package com.lelouch.cheeseandcream.service;

import com.lelouch.cheeseandcream.model.product.CategoryResponse;
import java.util.List;

public interface CategoryCrudService {

    void createCategory(String name);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    void updateCategory(Long id, String name);
    void deleteCategory(Long id);

}
