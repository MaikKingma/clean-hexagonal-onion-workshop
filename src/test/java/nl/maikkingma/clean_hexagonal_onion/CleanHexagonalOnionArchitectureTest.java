package nl.maikkingma.clean_hexagonal_onion;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * @author Maik Kingma
 */

@AnalyzeClasses(packages = "nl.maikkingma.clean_hexagonal_onion", importOptions = {ImportOption.DoNotIncludeTests.class})
public class CleanHexagonalOnionArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture().consideringAllDependencies()

            .layer("command").definedBy("nl.maikkingma.clean_hexagonal_onion.command..")
            .layer("query").definedBy("nl.maikkingma.clean_hexagonal_onion.query..")
            .layer("data").definedBy("nl.maikkingma.clean_hexagonal_onion.data..")
            .layer("domain interaction").definedBy("nl.maikkingma.clean_hexagonal_onion.domaininteraction..")
            .layer("domain").definedBy("nl.maikkingma.clean_hexagonal_onion.domain..")

            .whereLayer("command").mayNotBeAccessedByAnyLayer()
            .whereLayer("query").mayNotBeAccessedByAnyLayer()
            .whereLayer("data").mayNotBeAccessedByAnyLayer()
            .whereLayer("domain interaction").mayOnlyBeAccessedByLayers("command", "query", "data")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("domain interaction");
}
