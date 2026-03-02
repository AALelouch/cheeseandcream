package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
