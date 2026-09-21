package com.lelouch.cheeseandcream.application.product.command;

import com.lelouch.cheeseandcream.domain.Product;

public interface SaveProductCommand {

    void save(Product product);
}
