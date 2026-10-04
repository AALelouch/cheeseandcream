package com.lelouch.cheeseandcream.infra.operatingcost.adapter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lelouch.cheeseandcream.infra.operatingcost.persistence.OperatingCostEntity;
import com.lelouch.cheeseandcream.infra.operatingcost.persistence.OperatingCostRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OperatingCostJpaAdapterTest {

    @Test
    void deleteUsesTheProjectSoftDeleteConvention() {
        OperatingCostRepository repository = mock(OperatingCostRepository.class);
        OperatingCostEntity entity = OperatingCostEntity.from("Rent", 900_000D);
        when(repository.findById(5L)).thenReturn(Optional.of(entity));
        OperatingCostJpaAdapter adapter = new OperatingCostJpaAdapter(repository);

        adapter.deleteOperatingCost(5L);

        assertFalse(entity.getActive());
        verify(repository).save(entity);
        verify(repository, never()).delete(entity);
    }
}
