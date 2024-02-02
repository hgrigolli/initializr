package dev.grigolli.initializr.generator.files.ddd.domain;

import dev.grigolli.initializr.generator.core.AbstractTemplatePackageContributor;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;

public class DomainPackageContributor extends AbstractTemplatePackageContributor {

    public DomainPackageContributor(MustacheTemplateRenderer mustacheTemplateRenderer, ProjectDescription projectDescription) {
        super(mustacheTemplateRenderer, projectDescription);
    }

    @Override
    protected String getMustacheTemplateBasePath() {
        return "src/main/resources/templates/ddd/domain";
    }

    @Override
    protected String getModule() {
        return "domain";
    }
}
