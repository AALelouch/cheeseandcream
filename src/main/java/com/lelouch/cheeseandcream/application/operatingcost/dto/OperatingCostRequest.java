package com.lelouch.cheeseandcream.application.operatingcost.dto;

import com.lelouch.cheeseandcream.domain.OperatingCost;

public record OperatingCostRequest(String concept, Double amount) {

}
