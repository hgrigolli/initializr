package dev.grigolli.initializr.generator.common;

import org.springframework.context.annotation.Bean;

import io.spring.initializr.generator.project.ProjectGenerationConfiguration;

/**
 * Generate a project with Java & Moi convention.
 */
@ProjectGenerationConfiguration
class CommonSpringBootConfiguration {

    /*@Bean
    ApplicationYamlContributor applicationYamlContributor() {
        return new ApplicationYamlContributor();
    }*/

    @Bean
    DeleteAplicationPropertiesContributor deleteAplicationPropertiesContributor() {
        return new DeleteAplicationPropertiesContributor();
    }

}
