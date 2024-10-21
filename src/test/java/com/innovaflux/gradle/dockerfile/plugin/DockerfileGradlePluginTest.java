package com.innovaflux.gradle.dockerfile.plugin;

import com.innovaflux.gradle.dockerfile.plugin.extension.GenerateDockerfileExtension;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the DockerfileGradlePlugin.
 *
 * @author Jerome Wolff
 * @version 1.0.0
 * @since 1.0.0
 */
class DockerfileGradlePluginTest {
  /**
   * The directory used for the test.
   */
  private File projectDirectory;
  /**
   * The path were the build file is located.
   */
  private Path buildFilePath;

  /**
   * Create a temporary directory for the test.
   * The directory will be deleted after the test.
   *
   * @throws IOException if an I/O error occurs while creating the directory
   */
  @BeforeEach
  void setup() throws IOException {
    this.projectDirectory = Files.createTempDirectory("gradle-test").toFile();
    this.buildFilePath = Paths.get(this.projectDirectory.getAbsolutePath(), "build.gradle.kts");
  }

  /**
   * Delete the temporary directory used for the test.
   * If the directory is not empty, it will be deleted recursively.
   */
  @AfterEach
  void teardown() {
    if (this.projectDirectory.isDirectory()) {
      deleteDirectory(this.projectDirectory);
    }
  }

  /**
   * Test the generation of a Dockerfile with default values. The build file contains the following configuration:
   * <pre>
   *     plugins {
   *         id("com.innovaflux.dockerfile-plugin")
   *     }
   * </pre>
   * <p>
   * The expected Dockerfile content is:
   * <pre>
   *     FROM eclipse-temurin:21-jdk-alpine
   *     WORKDIR /app
   *     COPY build/application.jar /app/app.jar
   *     ENTRYPOINT ["java", "-jar", "/app/app.jar"]
   *     EXPOSE 8080
   * </pre>
   *
   * @throws IOException if an I/O error occurs while writing the build file
   */
  @Test
  void testDefaultDockerfileGeneration() throws IOException {
    Files.writeString(this.buildFilePath, """
      plugins {
          id("com.innovaflux.dockerfile-plugin")
      }
      """);
    var result = GradleRunner.create()
      .withProjectDir(this.projectDirectory)
      .withArguments("generateDockerfile")
      .withPluginClasspath()
      .build();
    var task = result.task(":generateDockerfile");
    assertNotNull(task);
    assertEquals(task.getOutcome(), TaskOutcome.SUCCESS);
    assertTrue(result.getOutput().contains("Dockerfile generated at"));
    var dockerfile = new File(this.projectDirectory, "Dockerfile");
    assertTrue(dockerfile.exists());
    var dockerfileContent = Files.readString(dockerfile.toPath());
    assertTrue(dockerfileContent.contains("FROM %s".formatted(GenerateDockerfileExtension.DEFAULT_IMAGE)));
    assertTrue(dockerfileContent.contains("COPY %s/%s /app/app.jar".formatted(GenerateDockerfileExtension.DEFAULT_BUILD_DIRECTORY, GenerateDockerfileExtension.DEFAULT_JAR_FILE_NAME_TEMPLATE.formatted(this.projectDirectory.getName()))));
    assertTrue(dockerfileContent.contains("EXPOSE %d".formatted(GenerateDockerfileExtension.DEFAULT_EXPOSE_PORT)));
  }

  /**
   * Test the generation of a Dockerfile with custom values. The build file contains the following configuration:
   * <pre>
   *     plugins {
   *         id("com.innovaflux.dockerfile-plugin")
   *     }
   *
   *     dockerfileConfiguration {
   *         image.set("eclipse-temurin:21-jdk-alpine")
   *         buildDirectory.set("build")
   *         jarFileName.set("application.jar")
   *         jvmArguments.set("-Xmx512m")
   *         exposePort.set(8000)
   *     }
   * </pre>
   * <p>
   * The expected Dockerfile content is:
   * <pre>
   *     FROM eclipse-temurin:21-jdk-alpine
   *     WORKDIR /app
   *     COPY build/application.jar /app/app.jar
   *     ENV JAVA_OPTS "-Xmx512m"
   *     ENTRYPOINT ["java", "-Xmx512m" "-jar", "/app/app.jar"]
   *     EXPOSE 8000
   * </pre>
   *
   * @throws IOException if an I/O error occurs while writing the build file
   */
  @Test
  void testCustomDockerfileGeneration() throws IOException {
    var image = "openjdk:11-jre-slim";
    var buildDirectory = "build";
    var jarFileName = "application.jar";
    var jvmArguments = "-Xmx512m";
    var exposePort = 8000;
    Files.writeString(this.buildFilePath, "plugins {\n" +
                                          "    id(\"com.innovaflux.dockerfile-plugin\")\n" +
                                          "}\n" +
                                          " \n" +
                                          "dockerfileConfiguration {\n" +
                                          "    image.set(\"" + image + "\")\n" +
                                          "    buildDirectory.set(\"" + buildDirectory + "\")\n" +
                                          "    jarFileName.set(\"" + jarFileName + "\")\n" +
                                          "    jvmArguments.set(\"" + jvmArguments + "\")\n" +
                                          "    exposePort.set(" + exposePort + ")\n" +
                                          "}\n" +
                                          " ");
    var result = GradleRunner
      .create()
      .withProjectDir(this.projectDirectory)
      .withArguments("generateDockerfile")
      .withPluginClasspath()
      .build();
    var task = result.task(":generateDockerfile");
    assertNotNull(task);
    assertEquals(task.getOutcome(), TaskOutcome.SUCCESS);
    assertTrue(result.getOutput().contains("Dockerfile generated at"));
    var dockerfile = Paths.get(this.projectDirectory.toString(), "Dockerfile").toFile();
    assertTrue(dockerfile.exists());
    var dockerfileContent = Files.readString(dockerfile.toPath());
    assertTrue(dockerfileContent.contains("FROM %s".formatted(image)));
    assertTrue(dockerfileContent.contains("COPY %s/%s /app/app.jar".formatted(buildDirectory, jarFileName)));
    assertTrue(dockerfileContent.contains("EXPOSE %d".formatted(exposePort)));
  }

  /**
   * Delete the temporary directory used for the test.
   * If the directory is not empty, it will be deleted recursively.
   *
   * @param directory the directory to delete
   */
  static void deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      var files = directory.listFiles();
      if (files != null) {
        Arrays.stream(files)
          .forEach(DockerfileGradlePluginTest::deleteDirectory);
      }
    }
    if (!directory.delete()) {
      throw new RuntimeException("Failed to delete directory: " + directory);
    }
  }
}
