package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByAgentIdAndActiveIsTrue(Long agentId);
    Optional<Product> findByIdAndActiveIsTrue(Long id);
    List<Product> findAllByActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentId(String name, Long id, Long agentId);

}
