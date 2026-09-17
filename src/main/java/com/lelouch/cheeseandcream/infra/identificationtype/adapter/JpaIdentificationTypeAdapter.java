package com.lelouch.cheeseandcream.infra.identificationtype.adapter;

import com.lelouch.cheeseandcream.application.identificationtype.FindAllIdentificationTypesQuery;
import com.lelouch.cheeseandcream.application.identificationtype.FindIdentificationTypeByIdQuery;
import com.lelouch.cheeseandcream.application.identificationtype.IdentificationTypeNameExistsQuery;
import com.lelouch.cheeseandcream.application.identificationtype.SaveIdentificationTypeCommand;
import com.lelouch.cheeseandcream.domain.IdentificationType;
import com.lelouch.cheeseandcream.infra.identificationtype.IdentificationTypeJpaMapper;
import com.lelouch.cheeseandcream.infra.identificationtype.persistence.IdentificationTypeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class JpaIdentificationTypeAdapter implements SaveIdentificationTypeCommand,
        FindIdentificationTypeByIdQuery, FindAllIdentificationTypesQuery, IdentificationTypeNameExistsQuery {

    private final IdentificationTypeRepository identificationTypeRepository;
    private final IdentificationTypeJpaMapper identificationTypeJpaMapper;

    public JpaIdentificationTypeAdapter(IdentificationTypeRepository identificationTypeRepository,
            IdentificationTypeJpaMapper identificationTypeJpaMapper) {
        this.identificationTypeRepository = identificationTypeRepository;
        this.identificationTypeJpaMapper = identificationTypeJpaMapper;
    }

    @Override
    public void save(IdentificationType identificationType) {
        identificationTypeRepository.save(identificationTypeJpaMapper.toEntity(identificationType));
    }

    @Override
    public Optional<IdentificationType> findById(Long id) {
        return identificationTypeRepository.findByIdAndActiveIsTrue(id).map(identificationTypeJpaMapper::toDomain);
    }

    @Override
    public List<IdentificationType> findAll() {
        return identificationTypeRepository.findAllByActiveIsTrue().stream()
                .map(identificationTypeJpaMapper::toDomain).toList();
    }

    @Override
    public boolean exists(String name, Long excludedId) {
        return excludedId == null
                ? identificationTypeRepository.existsByNameAndActiveIsTrue(name)
                : identificationTypeRepository.existsByNameAndActiveIsTrueAndIdNot(name, excludedId);
    }
}
