package com.lelouch.cheeseandcream.application.category.query;

import com.lelouch.cheeseandcream.domain.Category;
import java.util.List;

public interface FindAllCategoriesQuery {

    List<Category> findAll();
}
