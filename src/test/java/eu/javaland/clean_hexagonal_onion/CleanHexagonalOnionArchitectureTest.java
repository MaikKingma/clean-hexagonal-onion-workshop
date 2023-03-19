package eu.javaland.clean_hexagonal_onion;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import eu.javaland.clean_hexagonal_onion.data.book.BookJPA;
import eu.javaland.clean_hexagonal_onion.domain.DomainEvent;
import eu.javaland.clean_hexagonal_onion.domain.book.Book;
import eu.javaland.clean_hexagonal_onion.process.EventProcessor;
import eu.javaland.clean_hexagonal_onion.process.book.PublishBookDelegate;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * @author Maik Kingma
 */

@AnalyzeClasses(packages = "eu.javaland.clean_hexagonal_onion", importOptions = {ImportOption.DoNotIncludeTests.class})
public class CleanHexagonalOnionArchitectureTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected =
            layeredArchitecture().consideringAllDependencies()

                    .layer("command").definedBy("eu.javaland.clean_hexagonal_onion.command..")
                    .layer("query").definedBy("eu.javaland.clean_hexagonal_onion.query..")
                    .layer("data").definedBy("eu.javaland.clean_hexagonal_onion.data..")
                    .layer("acl").definedBy("eu.javaland.clean_hexagonal_onion.acl..")
                    .layer("process").definedBy("eu.javaland.clean_hexagonal_onion.process..")
                    .layer("domain interaction").definedBy("eu.javaland.clean_hexagonal_onion.domaininteraction..")
                    .layer("domain").definedBy("eu.javaland.clean_hexagonal_onion.domain..")

                    .whereLayer("command").mayNotBeAccessedByAnyLayer()
                    .whereLayer("query").mayNotBeAccessedByAnyLayer()
                    .whereLayer("data").mayNotBeAccessedByAnyLayer()
                    .whereLayer("acl").mayNotBeAccessedByAnyLayer()
                    .whereLayer("process").mayNotBeAccessedByAnyLayer()
                    .whereLayer("domain interaction").mayOnlyBeAccessedByLayers("command", "query", "data", "acl", "process")
                    .whereLayer("domain").mayOnlyBeAccessedByLayers("domain interaction")
                    // we will ignore the Domain Event dependencies from the process layer to the domain layer
                    // We are eventually trying to solve complexity, not add to it. Adding another layer to solve
                    // this would be overkill and overcomplicate things
                    .ignoreDependency(EventProcessor.class, Book.RequestPublishingEvent.class)
                    .ignoreDependency(PublishBookDelegate.class, Book.RequestPublishingEvent.class)
                    .ignoreDependency(BookJPA.class, DomainEvent.class)
            ;
}
