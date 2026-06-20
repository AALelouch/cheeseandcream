package com.lelouch.cheeseandcream.service.impl;

import com.lelouch.cheeseandcream.ValidatorUtils;
import com.lelouch.cheeseandcream.entity.agent.IdentificationType;
import com.lelouch.cheeseandcream.repository.IdentificationTypeRepository;
import com.lelouch.cheeseandcream.service.IdentificationTypeCrudService;
import com.lelouch.cheeseandcream.model.agent.IdentificationTypeResponse;
import com.lelouch.cheeseandcream.model.agent.IdentificationTypeRequest;
import java.util.List;
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
        identificationTypeRepository.save(new IdentificationType(null, identificationTypeRequest.name(), true));
    }

    @Override
    public void updateIdentificationType(Long id, IdentificationTypeRequest identificationTypeRequest) {
        ValidatorUtils.validateData(() -> identificationTypeRepository.existsByNameAndIdNot(identificationTypeRequest.name(), id), "Identification type with the same name already exists");
        IdentificationType identificationType = identificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Identification type not found"));
        identificationType.setName(identificationType.getName());
        identificationTypeRepository.save(identificationType);
    }

    @Override
    public void deleteIdentificationType(Long id) {
        IdentificationType identificationType = identificationTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Identification type not found"));
        identificationType.setActive(false);
        identificationTypeRepository.save(identificationType);
    }

    @Override
    public List<IdentificationTypeResponse> getAllIdentificationTypes() {
        return identificationTypeRepository.findAllByActiveIsTrue().stream()
                .map(identificationType -> new IdentificationTypeResponse(identificationType.getId(), identificationType.getName()))
                .toList();
    }
}
