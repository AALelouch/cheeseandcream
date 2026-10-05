package com.lelouch.cheeseandcream.infra.financialoperation.persistence.projection;

public interface OperationProductDetailsProjection {

    Long getProductId();
    String getProductName();
    Double getQuantity();
    Double getPrice();
    Double getTotalPrice();

}
