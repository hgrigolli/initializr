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

public abstract class AbstractTemplatePackageContributor implements ProjectContributor {

    private final MustacheTemplateRenderer mustacheTemplateRenderer;

    private final ProjectDescription projectDescription;

    private Path projectRoot;

    protected AbstractTemplatePackageContributor(
            MustacheTemplateRenderer mustacheTemplateRenderer,
            ProjectDescription projectDescription)
    {
        this.mustacheTemplateRenderer = mustacheTemplateRenderer;
        this.projectDescription = projectDescription;
    }

    @Override
    public final void contribute(Path projectRoot) throws IOException {
        this.projectRoot = projectRoot;
        String relativePath = getMustacheTemplateBasePath();
        String absolutePath = new File(relativePath).getAbsolutePath();
        File directory = new File(absolutePath);
        // Check if the path exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            iterateFilesAndFolders(directory);
        } else {
            System.out.println("Invalid directory path.");
        }

    }

    public void iterateFilesAndFolders(File directory) throws IOException {
        // List files and directories
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    iterateFilesAndFolders(file);
                } else {
                    Path output = createEmptyFile(file.toPath());
                    String rendered = renderTemplate(getTemplateRelativePath(file.toPath()).replace(".mustache", ""));
                    writeFile(output, rendered);
                }
            }
        }
    }

    protected Path createEmptyFile(Path filePath) throws IOException {

        final var output = Path.of(getOutputRelativePath(filePath));

        final var outputAbsolutePath = projectRoot.resolve(output);

        System.out.println(outputAbsolutePath);
        if (!Files.exists(outputAbsolutePath)) {
            Files.createDirectories(outputAbsolutePath.getParent());
            Files.createFile(outputAbsolutePath);
        }
        return outputAbsolutePath;
    }

    protected String renderTemplate(String mustacheTemplate) {
        Map<String, String> model = prepareModel();
        return mustacheTemplateRenderer.render(mustacheTemplate, model);
    }

    protected void writeFile(Path output, String rendered) throws IOException {
        FileCopyUtils.copy(rendered.getBytes(StandardCharsets.UTF_8), Files.newOutputStream(output, StandardOpenOption.APPEND));
    }

    protected abstract String getMustacheTemplateBasePath();

    public ProjectDescription getProjectDescription() {
        return projectDescription;
    }

    protected String getOutputRelativePath(Path filePath) {

        StringBuilder sb = new StringBuilder();

        String[] strings = filePath.toAbsolutePath().toString().split("java");

        sb.append(getModule()).append(File.separator).append("src/main/java/");
        sb.append(projectDescription.getPackageName().replace(".", "/")).append(File.separator).append(getModule());
        sb.append(strings[1].replace("mustache", "java"));


        return sb.toString();
    }

    protected String getTemplateRelativePath(Path filePath) {
        StringBuilder sb = new StringBuilder();

        final var absPath = filePath.toAbsolutePath().toString();

        String[] paths = absPath.split("templates");

        sb.append(paths[1].replace(".mustache", ""));

        return sb.toString();
    }

    protected Map<String, String> prepareModel() {
        String packageName = getProjectDescription().getPackageName();
        return Collections.singletonMap("package", packageName);
    }

    protected abstract String getModule();

}
