package dev.grigolli.initializr.generator.files.ddd;

import dev.grigolli.initializr.generator.files.ddd.application.ApplicationPackageContributor;
import dev.grigolli.initializr.generator.files.ddd.domain.DomainPackageContributor;
import dev.grigolli.initializr.generator.core.InitializrConstants;
import dev.grigolli.initializr.generator.files.ddd.infrastructure.InfrastructurePackageContributor;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.web.autoconfigure.InitializrAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency(InitializrConstants.DEPENDENCY_DDD)
@AutoConfigureAfter({InitializrAutoConfiguration.class})
class DDDConfiguration {

    @Bean
    DomainPackageContributor dddDomainMultipleResourcesContributor(ProjectDescription projectDescription, MustacheTemplateRenderer mustacheTemplateRenderer) {
        return new DomainPackageContributor(mustacheTemplateRenderer, projectDescription);
    }

    @Bean
    ApplicationPackageContributor dddApplicationMultipleResourcesContributor(ProjectDescription projectDescription, MustacheTemplateRenderer mustacheTemplateRenderer) {
        return new ApplicationPackageContributor(mustacheTemplateRenderer, projectDescription);
    }

    @Bean
    InfrastructurePackageContributor dddInfrastructureMultipleResourcesContributor(ProjectDescription projectDescription, MustacheTemplateRenderer mustacheTemplateRenderer) {
        return new InfrastructurePackageContributor(mustacheTemplateRenderer, projectDescription);
    }

    @Bean
    DeleteDefaultSourceFolderContributor deleteDefaultSourceFolderContributor() {
        return new DeleteDefaultSourceFolderContributor();
    }


}
