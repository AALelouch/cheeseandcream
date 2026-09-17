package com.lelouch.cheeseandcream.application.category;

import com.lelouch.cheeseandcream.domain.Category;
import java.util.List;

public interface FindAllCategoriesQuery {

    List<Category> findAll();
}
