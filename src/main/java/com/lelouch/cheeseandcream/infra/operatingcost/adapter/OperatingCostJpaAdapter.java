package com.lelouch.cheeseandcream.infra.operatingcost.adapter;

import com.lelouch.cheeseandcream.application.operatingcost.command.OperatingCostCommand;
import com.lelouch.cheeseandcream.application.operatingcost.dto.OperatingCostTermRequest;
import com.lelouch.cheeseandcream.application.operatingcost.query.OperatingCostQuery;
import com.lelouch.cheeseandcream.domain.OperatingCost;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import com.lelouch.cheeseandcream.infra.operatingcost.persistence.OperatingCostEntity;
import com.lelouch.cheeseandcream.infra.operatingcost.persistence.OperatingCostRepository;
import com.lelouch.cheeseandcream.infra.operatingcost.persistence.OperatingCostSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperatingCostJpaAdapter implements OperatingCostCommand, OperatingCostQuery {

    private final OperatingCostRepository operatingCostRepository;

    public OperatingCostJpaAdapter(OperatingCostRepository operatingCostRepository) {
        this.operatingCostRepository = operatingCostRepository;
    }

    @Override
    public void createOperatingCost(OperatingCost request) {
        OperatingCostEntity operatingCostEntity = OperatingCostEntity.from(request.getConcept(), request.getAmount());
        operatingCostRepository.save(operatingCostEntity);
    }

    @Override
    public void updateOperatingCost(Long id, OperatingCost request) {

        OperatingCostEntity operatingCostEntity = operatingCostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Operating cost not found with id: " + id));

        operatingCostEntity.setAmount(request.getAmount());
        operatingCostEntity.setConcept(request.getConcept());

        operatingCostRepository.save(operatingCostEntity);
    }

    @Override
    public void deleteOperatingCost(Long id) {
        OperatingCostEntity operatingCostEntity = operatingCostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Operating cost not found with id: " + id));

        operatingCostEntity.setActive(false);
        operatingCostRepository.save(operatingCostEntity);
    }

    @Override
    public Page<OperatingCost> getAllOperatingCost(Pageable pageable) {
        return operatingCostRepository.findAll(OperatingCostSpecification.isActive(), pageable).map(OperatingCostEntity::toDomain);
    }

    @Override
    public Page<OperatingCost> getOperatingCostByMonth(Pageable pageable, int month, int year) {
        return operatingCostRepository.findAll(OperatingCostSpecification.isActiveAndCreatedInMonth(month, year), pageable).map(OperatingCostEntity::toDomain);
    }

    @Override
    public Page<OperatingCost> searchOperatingCostByTerm(Pageable pageable, OperatingCostTermRequest request) {
        return operatingCostRepository.searchByTerm(request.term(), pageable).map(OperatingCostEntity::toDomain);
    }
}
