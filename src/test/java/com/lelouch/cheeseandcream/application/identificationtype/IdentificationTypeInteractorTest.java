package com.lelouch.cheeseandcream.application.identificationtype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lelouch.cheeseandcream.application.identificationtype.command.SaveIdentificationTypeCommand;
import com.lelouch.cheeseandcream.application.identificationtype.dto.IdentificationTypeRequest;
import com.lelouch.cheeseandcream.application.identificationtype.query.FindAllIdentificationTypesQuery;
import com.lelouch.cheeseandcream.application.identificationtype.query.FindIdentificationTypeByIdQuery;
import com.lelouch.cheeseandcream.application.identificationtype.query.IdentificationTypeNameExistsQuery;
import com.lelouch.cheeseandcream.domain.IdentificationType;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class IdentificationTypeInteractorTest {

    @Test
    void updatePersistsTheRequestedName() {
        RecordingStore store = new RecordingStore(new IdentificationType(3L, "CC", true));
        IdentificationTypeInteractor interactor = new IdentificationTypeInteractor(store, store, store, store,
                identificationTypes -> List.of());

        interactor.updateIdentificationType(3L, new IdentificationTypeRequest("NIT"));

        assertEquals("NIT", store.saved.name());
        assertEquals(3L, store.saved.id());
    }

    private static final class RecordingStore implements SaveIdentificationTypeCommand, FindIdentificationTypeByIdQuery,
            FindAllIdentificationTypesQuery, IdentificationTypeNameExistsQuery {
        private final IdentificationType existing;
        private IdentificationType saved;

        private RecordingStore(IdentificationType existing) {
            this.existing = existing;
        }

        @Override
        public void save(IdentificationType identificationType) {
            this.saved = identificationType;
        }

        @Override
        public Optional<IdentificationType> findById(Long id) {
            return existing.id().equals(id) ? Optional.of(existing) : Optional.empty();
        }

        @Override
        public List<IdentificationType> findAll() {
            return List.of(existing);
        }

        @Override
        public boolean exists(String name, Long excludedId) {
            return false;
        }
    }
}
