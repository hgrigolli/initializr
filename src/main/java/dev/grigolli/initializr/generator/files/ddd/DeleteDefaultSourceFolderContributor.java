package dev.grigolli.initializr.generator.files.ddd;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DeleteDefaultSourceFolderContributor implements ProjectContributor {

    @Override
    public void contribute(Path projectRoot) throws IOException {
        var relativePath = "src";
        Path output = projectRoot.resolve(relativePath);
        Files.deleteIfExists(output);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}