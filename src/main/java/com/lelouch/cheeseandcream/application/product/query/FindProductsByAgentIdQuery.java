package com.lelouch.cheeseandcream.application.product.query;

import com.lelouch.cheeseandcream.application.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindProductsByAgentIdQuery {

    Page<ProductResponse> findByAgentId(Long agentId, Pageable pageable);
}
