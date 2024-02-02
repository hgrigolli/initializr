package dev.grigolli.initializr.generator.files.ddd.application;

import dev.grigolli.initializr.generator.core.AbstractTemplatePackageContributor;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;

public class ApplicationPackageContributor extends AbstractTemplatePackageContributor {

    public ApplicationPackageContributor(MustacheTemplateRenderer mustacheTemplateRenderer, ProjectDescription projectDescription) {
        super(mustacheTemplateRenderer, projectDescription);
    }

    @Override
    protected String getMustacheTemplateBasePath() {
        return "src/main/resources/templates/ddd/application";
    }

    @Override
    protected String getModule() {
        return "application";
    }
}