package com.lelouch.cheeseandcream.infra.repository;

import com.lelouch.cheeseandcream.infra.orm.product.CategoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByActiveIsTrue();
    Optional<CategoryEntity> findByIdAndActiveIsTrue(Long id);
    boolean existsByNameAndActiveIsTrue(String name);
    boolean existsByNameAndActiveIsTrueAndIdNot(String name,Long id);


}
