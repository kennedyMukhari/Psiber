package kennedy.co.za;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("kennedy.co.za");

        noClasses()
            .that()
                .resideInAnyPackage("kennedy.co.za.service..")
            .or()
                .resideInAnyPackage("kennedy.co.za.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..kennedy.co.za.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
