package com.lelouch.cheeseandcream.application.identificationtype;

import com.lelouch.cheeseandcream.domain.IdentificationType;
import com.lelouch.cheeseandcream.domain.ValidatorUtils;
import com.lelouch.cheeseandcream.domain.exception.NotFoundException;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IdentificationTypeInteractor implements IdentificationTypeUseCase {

    private final SaveIdentificationTypeCommand saveIdentificationTypeCommand;
    private final FindIdentificationTypeByIdQuery findIdentificationTypeByIdQuery;
    private final FindAllIdentificationTypesQuery findAllIdentificationTypesQuery;
    private final IdentificationTypeNameExistsQuery identificationTypeNameExistsQuery;
    private final IdentificationTypeOutputPort identificationTypeOutputPort;

    public IdentificationTypeInteractor(SaveIdentificationTypeCommand saveIdentificationTypeCommand,
            FindIdentificationTypeByIdQuery findIdentificationTypeByIdQuery,
            FindAllIdentificationTypesQuery findAllIdentificationTypesQuery,
            IdentificationTypeNameExistsQuery identificationTypeNameExistsQuery,
            IdentificationTypeOutputPort identificationTypeOutputPort) {
        this.saveIdentificationTypeCommand = saveIdentificationTypeCommand;
        this.findIdentificationTypeByIdQuery = findIdentificationTypeByIdQuery;
        this.findAllIdentificationTypesQuery = findAllIdentificationTypesQuery;
        this.identificationTypeNameExistsQuery = identificationTypeNameExistsQuery;
        this.identificationTypeOutputPort = identificationTypeOutputPort;
    }

    @Override
    @Transactional
    @CacheEvict(value = "identificationTypes", allEntries = true)
    public void createIdentificationType(IdentificationTypeRequest request) {
        ValidatorUtils.validateData(() -> identificationTypeNameExistsQuery.exists(request.name(), null), "Identification type with the same name already exists");
        saveIdentificationTypeCommand.save(IdentificationType.create(request.name()));
    }

    @Override
    @Transactional
    @CacheEvict(value = "identificationTypes", allEntries = true)
    public void updateIdentificationType(Long id, IdentificationTypeRequest request) {
        ValidatorUtils.validateData(() -> identificationTypeNameExistsQuery.exists(request.name(), id), "Identification type with the same name already exists");
        saveIdentificationTypeCommand.save(findActiveIdentificationType(id).rename(request.name()));
    }

    @Override
    @Transactional
    @CacheEvict(value = "identificationTypes", allEntries = true)
    public void deleteIdentificationType(Long id) {
        saveIdentificationTypeCommand.save(findActiveIdentificationType(id).deactivate());
    }

    @Override
    @Cacheable(value = "identificationTypes")
    public List<IdentificationTypeResponse> getAllIdentificationTypes() {
        return identificationTypeOutputPort.mapToResponse(findAllIdentificationTypesQuery.findAll());
    }

    private IdentificationType findActiveIdentificationType(Long id) {
        return findIdentificationTypeByIdQuery.findById(id)
                .orElseThrow(() -> new NotFoundException("Identification type not found"));
    }
}
