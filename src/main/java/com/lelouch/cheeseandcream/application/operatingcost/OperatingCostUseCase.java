package com.lelouch.cheeseandcream.application.operatingcost;

import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostRequest;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperatingCostUseCase {

    void createOperatingCost(OperatingCostRequest request);
    void updateOperatingCost(Long id, OperatingCostRequest request);
    void deleteOperatingCost(Long id);
    Page<OperatingCostResponse> getAllOperatingCost(Pageable pageable);
    Page<OperatingCostResponse> getOperatingCostByMonth(Pageable pageable, int month, int year);
    Page<OperatingCostResponse> searchOperatingCostByTerm(Pageable pageable, OperatingCostTermRequest request);



}
