package com.lelouch.cheeseandcream.architecture;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArchitectureRulesTest {

    private static final Path SOURCE_ROOT = Path.of("src/main/java/com/lelouch/cheeseandcream");

    @Test
    void applicationAndDomainDoNotDependOnInfrastructure() throws IOException {
        for (String layer : List.of("application", "domain")) {
            try (var files = Files.walk(SOURCE_ROOT.resolve(layer))) {
                List<Path> violations = files.filter(path -> path.toString().endsWith(".java"))
                        .filter(this::importsInfrastructure)
                        .toList();
                assertTrue(violations.isEmpty(), () -> layer + " must not import infrastructure: " + violations);
            }
        }
    }

    @Test
    void controllersDoNotUseLegacyServices() throws IOException {
        try (var files = Files.walk(SOURCE_ROOT.resolve("infra"))) {
            List<Path> violations = files.filter(path -> path.getFileName().toString().endsWith("Controller.java"))
                    .filter(path -> contains(path, "application.service"))
                    .toList();
            assertTrue(violations.isEmpty(), () -> "Controllers must depend on use cases: " + violations);
        }
    }

    @Test
    void legacyLayerPackagesAreEmpty() throws IOException {
        List<Path> legacyDirectories = List.of(
                SOURCE_ROOT.resolve("application/service"),
                SOURCE_ROOT.resolve("application/model"),
                SOURCE_ROOT.resolve("infra/controller"),
                SOURCE_ROOT.resolve("infra/mapper"),
                SOURCE_ROOT.resolve("infra/orm"),
                SOURCE_ROOT.resolve("infra/repository"));

        for (Path legacyDirectory : legacyDirectories) {
            if (!Files.exists(legacyDirectory)) {
                continue;
            }
            try (var files = Files.walk(legacyDirectory)) {
                assertFalse(files.anyMatch(path -> path.toString().endsWith(".java")),
                        () -> "Legacy package must stay empty: " + legacyDirectory);
            }
        }
    }

    @Test
    void jpaEntitiesLiveInsideFeaturePersistencePackages() throws IOException {
        try (var files = Files.walk(SOURCE_ROOT.resolve("infra"))) {
            List<Path> violations = files.filter(path -> path.toString().endsWith("Entity.java"))
                    .filter(path -> !path.toString().replace('\\', '/').contains("/persistence/"))
                    .toList();
            assertTrue(violations.isEmpty(), () -> "JPA entities must belong to feature persistence: " + violations);
        }
    }

    private boolean importsInfrastructure(Path path) {
        return contains(path, "import com.lelouch.cheeseandcream.infra");
    }

    private boolean contains(Path path, String value) {
        try {
            return Files.readString(path).contains(value);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot inspect " + path, exception);
        }
    }
}
