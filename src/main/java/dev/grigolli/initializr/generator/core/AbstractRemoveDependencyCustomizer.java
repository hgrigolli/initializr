package dev.grigolli.initializr.generator.core;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.core.Ordered;

public abstract class AbstractRemoveDependencyCustomizer implements BuildCustomizer<MavenBuild> {

    private final String dependency;

    protected AbstractRemoveDependencyCustomizer(String dependency) {
        this.dependency = dependency;
    }

    @Override
    public void customize(MavenBuild build) {
        build.dependencies().remove(dependency);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
