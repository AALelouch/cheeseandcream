package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.product.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByActiveIsTrue();
    Optional<Category> findByIdAndActiveIsTrue(Long id);
    boolean existsByNameAndActiveIsTrue(String name);
    boolean existsByNameAndActiveIsTrueAndIdNot(String name,Long id);


}
