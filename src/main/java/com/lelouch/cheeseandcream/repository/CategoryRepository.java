package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
