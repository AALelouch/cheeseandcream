package com.lelouch.cheeseandcream.infra.product.persistence.projection;

public interface ProductFullProjection {

    Long getId();
    String getName();
    Double getQuantity();
    Double getCost();
    String getUnitType();
    String getCategoryName();
}
