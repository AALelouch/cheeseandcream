package com.lelouch.cheeseandcream.infra.product.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByAgentEntityIdAndActiveIsTrue(Long agentId, Pageable pageable);
    Optional<ProductEntity> findByIdAndActiveIsTrue(Long id);
    List<ProductEntity> findAllByIdInAndActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentEntityId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(String name, Long id, Long agentId);

}
