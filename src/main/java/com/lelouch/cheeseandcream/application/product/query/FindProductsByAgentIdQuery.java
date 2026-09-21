package com.lelouch.cheeseandcream.application.product.query;

import com.lelouch.cheeseandcream.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProductsByAgentIdQuery {

    Page<Product> findByAgentId(Long agentId, Pageable pageable);
}
