package com.innovaflux.gradle.dockerfile.plugin.task;

import com.google.common.base.Preconditions;
import com.innovaflux.gradle.dockerfile.plugin.extension.GenerateDockerfileExtension;
import com.innovaflux.gradle.dockerfile.plugin.file.DockerfileFileFactory;
import com.innovaflux.gradle.dockerfile.plugin.file.writer.DockerfileFileWriter;
import com.innovaflux.gradle.dockerfile.plugin.file.writer.LocalDockerfileFileWriter;
import lombok.Setter;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Gradle task to generate a Dockerfile. The task is executed automatically when the gradle build is executed.
 * The task generates a Dockerfile in the project directory. The Dockerfile is generated using the extension properties.
 * The extension properties can be configured in the build script or in the gradle.properties file.
 *
 * @author Jerome Wolff
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
public class GenerateDockerFileTask extends DefaultTask {
  private final DockerfileFileFactory dockerfileFileFactory;
  private final DockerfileFileWriter dockerfileFileWriter;
  private final Path dockerfilePath;
  private GenerateDockerfileExtension extension;

  public GenerateDockerFileTask() throws IOException {
    this.dockerfileFileFactory = DockerfileFileFactory.create();
    var project = this.getProject();
    var projectDirectory = project.getLayout().getProjectDirectory();
    this.dockerfilePath = this.getDockerfilePath(projectDirectory);
    this.dockerfileFileWriter = LocalDockerfileFileWriter.createDefault(this.dockerfilePath);
  }

  /**
   * Task action to generate the Dockerfile.
   */
  @TaskAction
  public void generateDockerfile() throws Exception {
    Preconditions.checkNotNull(this.extension, "Extension is not set");
    Preconditions.checkNotNull(this.dockerfileFileFactory, "DockerfileFileFactory is not set");
    this.writeDockerfile();
    this.dockerfileFileWriter.close();
  }

  /**
   * Writes the Dockerfile to the specified path. The Dockerfile is written in the following format:
   * <pre>
   *  FROM {image}
   *  WORKDIR /app
   *  COPY {buildDirectory}/{jarFileName} /app/app.jar
   *  ENV JAVA_OPTS "{jvmArguments}"
   *  ENTRYPOINT ["java", "-jar", "/app/app.jar"]
   *  EXPOSE {exposePort}
   * </pre>
   */
  private void writeDockerfile() {
    try {
      var dockerfileFile = this.dockerfileFileFactory.createDockerfile(this.extension);
      this.dockerfileFileWriter.writeDockerfile(dockerfileFile);
      this.logLifecycle("Dockerfile generated at: %s".formatted(this.dockerfilePath.toAbsolutePath()));
    } catch (Exception ex) {
      throw new RuntimeException("Failed to generate dockerfilePath", ex);
    }
  }

  /**
   * Used to log a lifecycle message.
   *
   * @param message the message to log
   */
  private void logLifecycle(String message) {
    getLogger().lifecycle(message);
  }

  /**
   * Get the path to the Dockerfile. The path is relative to the project directory.
   *
   * @param projectDirectory the project directory
   * @return the path to the Dockerfile
   */
  private Path getDockerfilePath(Directory projectDirectory) {
    var projectDirectoryPath = projectDirectory.getAsFile().toPath();
    return projectDirectoryPath.resolve("Dockerfile");
  }
}
