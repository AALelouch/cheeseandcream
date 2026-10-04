package com.lelouch.cheeseandcream.application.operatingcost.query;

import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperatingCostQuery {

    Page<OperatingCost> getAllOperatingCost(Pageable pageable);
    Page<OperatingCost> getOperatingCostByMonth(Pageable pageable, int month, int year);
    Page<OperatingCost> searchOperatingCostByTerm(Pageable pageable, OperatingCostTermRequest request);

}
