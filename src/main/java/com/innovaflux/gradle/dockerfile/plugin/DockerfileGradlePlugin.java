package com.innovaflux.gradle.dockerfile.plugin;

import com.innovaflux.gradle.dockerfile.plugin.extension.GenerateDockerfileExtension;
import com.innovaflux.gradle.dockerfile.plugin.file.writer.LocalDockerfileFileWriter;
import com.innovaflux.gradle.dockerfile.plugin.task.GenerateDockerFileTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Gradle plugin to generate a Dockerfile. If this plugin is applied to the project it creates a task to generate the Dockerfile.
 * The task is executed automatically when the gradle build is executed.
 *
 * @author Jerome Wolff
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DockerfileGradlePlugin implements Plugin<Project> {
  /**
   * Apply the plugin to the project.
   *
   * @param project the project to apply the plugin to
   */
  @Override
  public void apply(@NotNull Project project) {
    var extension = project.getExtensions().create("dockerfileConfiguration", GenerateDockerfileExtension.class, project);
    this.createTasks(project, extension);
  }

  /**
   * Create the tasks for the plugin.
   *
   * @param project   the project to create the tasks for
   * @param extension the extension to use
   */
  private void createTasks(Project project, GenerateDockerfileExtension extension) {
    this.createGenerateDockerfileTask(project, extension);
  }

  /**
   * Create the task to generate the Dockerfile.
   *
   * @param project   the project to create the task for
   * @param extension the extension to use
   */
  private void createGenerateDockerfileTask(Project project, GenerateDockerfileExtension extension) {
    project.getTasks().create("generateDockerfile", GenerateDockerFileTask.class, generateDockerFileTask -> {
      generateDockerFileTask.setDescription("Generates a Dockerfile for the application");
      generateDockerFileTask.setGroup("docker");
      generateDockerFileTask.setExtension(extension);
    });
  }
}
