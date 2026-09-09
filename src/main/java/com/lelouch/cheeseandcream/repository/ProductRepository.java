package com.lelouch.cheeseandcream.repository;

import com.lelouch.cheeseandcream.entity.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByAgentIdAndActiveIsTrue(Long agentId, Pageable pageable);
    Optional<Product> findByIdAndActiveIsTrue(Long id);
    List<Product> findAllByIdInAndActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentId(String name, Long id, Long agentId);

}
