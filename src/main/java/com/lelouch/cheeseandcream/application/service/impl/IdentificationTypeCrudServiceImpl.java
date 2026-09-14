package com.lelouch.cheeseandcream.application.service.impl;

import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.infra.orm.agent.IdentificationTypeEntity;
import com.lelouch.cheeseandcream.infra.repository.IdentificationTypeRepository;
import com.lelouch.cheeseandcream.application.service.IdentificationTypeCrudService;
import com.lelouch.cheeseandcream.application.model.agent.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.application.model.agent.IdentificationTypeRequest;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class IdentificationTypeCrudServiceImpl implements IdentificationTypeCrudService {

    private final IdentificationTypeRepository identificationTypeRepository;

    public IdentificationTypeCrudServiceImpl(IdentificationTypeRepository identificationTypeRepository) {
        this.identificationTypeRepository = identificationTypeRepository;
    }

    @Override
    public void createIdentificationType(IdentificationTypeRequest identificationTypeRequest) {
        ValidatorUtils.validateData(() -> identificationTypeRepository.existsByName(identificationTypeRequest.name()), "Identification type with the same name already exists");
        identificationTypeRepository.save(new IdentificationTypeEntity(null, identificationTypeRequest.name(), true));
    }

    @Override
    public void updateIdentificationType(Long id, IdentificationTypeRequest identificationTypeRequest) {
        ValidatorUtils.validateData(() -> identificationTypeRepository.existsByNameAndIdNot(identificationTypeRequest.name(), id), "Identification type with the same name already exists");
        IdentificationTypeEntity identificationTypeEntity = identificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Identification type not found"));
        identificationTypeEntity.setName(identificationTypeEntity.getName());
        identificationTypeRepository.save(identificationTypeEntity);
    }

    @Override
    public void deleteIdentificationType(Long id) {
        IdentificationTypeEntity identificationTypeEntity = identificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Identification type not found"));
        identificationTypeEntity.setActive(false);
        identificationTypeRepository.save(identificationTypeEntity);
    }

    @Override
    @Cacheable(value = "identificationTypes")
    public List<IdentificationTypeResponse> getAllIdentificationTypes() {
        return identificationTypeRepository.findAllByActiveIsTrue().stream()
                .map(identificationType -> new IdentificationTypeResponse(identificationType.getId(), identificationType.getName()))
                .toList();
    }
}
