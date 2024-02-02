package dev.grigolli.initializr.generator.core;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractTemplateClassContributor implements ProjectContributor {

    private final MustacheTemplateRenderer mustacheTemplateRenderer;

    private final ProjectDescription projectDescription;

    private final String relativePath;

    protected AbstractTemplateClassContributor(
            MustacheTemplateRenderer mustacheTemplateRenderer,
            ProjectDescription projectDescription,
            String relativePath)
    {
        this.mustacheTemplateRenderer = mustacheTemplateRenderer;
        this.projectDescription = projectDescription;
        this.relativePath = relativePath;
    }

    @Override
    public final void contribute(Path projectRoot) throws IOException {
        Path output = createEmptyFile(projectRoot);
        String rendered = renderTemplate();
        writeFile(output, rendered);
    }

    protected Path createEmptyFile(Path projectRoot) throws IOException {
        Path output = projectRoot.resolve(getOutputRelativePath());
        if (!Files.exists(output)) {
            Files.createDirectories(output.getParent());
            Files.createFile(output);
        }
        return output;
    }

    protected String renderTemplate() {
        Map<String, String> model = prepareModel();
        return mustacheTemplateRenderer.render(getMustacheTemplate(), model);
    }

    protected void writeFile(Path output, String rendered) throws IOException {
        FileCopyUtils.copy(rendered.getBytes(StandardCharsets.UTF_8), Files.newOutputStream(output, StandardOpenOption.APPEND));
    }

    protected abstract String getMustacheTemplate();

    public ProjectDescription getProjectDescription() {
        return projectDescription;
    }

    protected String getOutputRelativePath() {
        String packageName = getProjectDescription().getPackageName() + getPackage();
        return relativePath + packageName.replace(".", "/") + File.separator + getJavaFilename();
    }

    protected Map<String, String> prepareModel() {
        String packageName = getProjectDescription().getPackageName() + getPackage();
        return Collections.singletonMap("package", packageName);
    }

    protected abstract String getJavaFilename();

    protected abstract String getPackage();
}
