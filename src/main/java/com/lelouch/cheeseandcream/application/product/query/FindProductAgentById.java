package com.lelouch.cheeseandcream.application.product.query;

import com.lelouch.cheeseandcream.domain.Product;
import java.util.Optional;

public interface FindProductAgentById {

    Optional<Product.Agent> findAgentById(Long agentId);
}
