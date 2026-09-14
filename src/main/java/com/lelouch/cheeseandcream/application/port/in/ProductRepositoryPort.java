package com.lelouch.cheeseandcream.application.port.in;

import com.lelouch.cheeseandcream.application.model.product.ProductResponse;
import com.lelouch.cheeseandcream.domain.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryPort {

    Page<ProductResponse> findByAgentEntityIdAndActiveIsTrue(Long agentId, Pageable pageable);
    Product findByIdAndActiveIsTrue(Long id);
    List<Product> findAllByIdInAndActiveIsTrue(List<Long> productIds);
    boolean existsByNameAndActiveIsTrueAndAgentEntityId(String name, Long  agentId);
    boolean existsByNameAndActiveIsTrueAndIdNotAndAgentEntityId(String name, Long id, Long agentId);

}
