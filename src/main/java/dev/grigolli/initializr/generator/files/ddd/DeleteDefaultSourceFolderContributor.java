package dev.grigolli.initializr.generator.files.ddd;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.core.Ordered;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class DeleteDefaultSourceFolderContributor implements ProjectContributor {

    @Override
    public void contribute(Path projectRoot) throws IOException {
        var relativePath = "src";
        Path output = projectRoot.resolve(relativePath);
        Files.walk(output)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}