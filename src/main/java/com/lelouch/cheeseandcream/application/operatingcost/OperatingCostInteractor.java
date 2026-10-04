package com.lelouch.cheeseandcream.application.operatingcost;

import com.lelouch.cheeseandcream.application.operatingcost.command.OperatingCostCommand;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostRequest;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostResponse;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import com.lelouch.cheeseandcream.application.operatingcost.query.OperatingCostQuery;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import com.lelouch.cheeseandcream.domain.exception.BadRequestException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperatingCostInteractor implements OperatingCostUseCase{

    private final OperatingCostCommand operatingCostCommand;
    private final OperatingCostQuery operatingCostQuery;
    private final OperatingCostOutputPort operatingCostOutputPort;

    public OperatingCostInteractor(OperatingCostCommand operatingCostCommand, OperatingCostQuery operatingCostQuery,
            OperatingCostOutputPort operatingCostOutputPort) {
        this.operatingCostCommand = operatingCostCommand;
        this.operatingCostQuery = operatingCostQuery;
        this.operatingCostOutputPort = operatingCostOutputPort;
    }

    @Override
    @CacheEvict(value = {"operatingCost", "operatingCostMonth"}, allEntries = true)
    public void createOperatingCost(OperatingCostRequest request) {
        operatingCostCommand.createOperatingCost(OperatingCost.create(request.concept(), request.amount()));
    }

    @Override
    @CacheEvict(value = {"operatingCost", "operatingCostMonth"}, allEntries = true)
    public void updateOperatingCost(Long id, OperatingCostRequest request) {
        operatingCostCommand.updateOperatingCost(id, OperatingCost.create(request.concept(), request.amount()));
    }

    @Override
    @CacheEvict(value = {"operatingCost", "operatingCostMonth"}, allEntries = true)
    public void deleteOperatingCost(Long id) {
        operatingCostCommand.deleteOperatingCost(id);
    }

    @Override
    @Cacheable("operatingCost")
    public Page<OperatingCostResponse> getAllOperatingCost(Pageable pageable) {
        return operatingCostOutputPort.mapToResponse(operatingCostQuery.getAllOperatingCost(pageable));
    }

    @Override
    @Cacheable("operatingCostMonth")
    public Page<OperatingCostResponse> getOperatingCostByMonth(Pageable pageable, int month, int year) {

        if (month < 1 || month > 12) {
            throw new BadRequestException("Month must be between 1 and 12");
        }

        if (year < 2026 || year > 2100) {
            throw new BadRequestException("Year must be between 2026 and 2100");
        }

        return operatingCostOutputPort.mapToResponse(operatingCostQuery.getOperatingCostByMonth(pageable, month, year));
    }

    @Override
    public Page<OperatingCostResponse> searchOperatingCostByTerm(Pageable pageable, OperatingCostTermRequest request) {
        return operatingCostOutputPort.mapToResponse(operatingCostQuery.searchOperatingCostByTerm(pageable, request));
    }
}
