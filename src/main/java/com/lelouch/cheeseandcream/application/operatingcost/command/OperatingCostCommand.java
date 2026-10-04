package com.lelouch.cheeseandcream.application.operatingcost.command;

import com.lelouch.cheeseandcream.domain.OperatingCost;

public interface OperatingCostCommand {

    void createOperatingCost(OperatingCost request);
    void updateOperatingCost(Long id, OperatingCost request);
    void deleteOperatingCost(Long id);

}
