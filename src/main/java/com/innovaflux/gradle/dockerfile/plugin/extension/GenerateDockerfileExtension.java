package com.innovaflux.gradle.dockerfile.plugin.extension;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;

/**
 * Extension class for the dockerfile-plugin. This class is used to configure the plugin.
 *
 * @author Jerome Wolff
 * @since 1.0.0
 * version 1.0.0
 */
@Getter
@EqualsAndHashCode
@ToString
public class GenerateDockerfileExtension {
  public static final String DEFAULT_IMAGE = "eclipse-temurin:21-jdk-alpine";
  public static final String DEFAULT_BUILD_DIRECTORY = "build/libs";
  public static final String DEFAULT_JAR_FILE_NAME_TEMPLATE = "%s.jar";
  public static final int DEFAULT_EXPOSE_PORT = 8080;
  private final Property<String> image;
  private final Property<String> buildDirectory;
  private final Property<String> jarFileName;
  private final Property<String> jvmArguments;
  private final Property<Integer> exposePort;

  /**
   * Create a new instance of the extension. The extension properties are initialized with default values. If the user
   * sets the properties, the default values are ignored.
   *
   * @param project the gradle project
   */
  public GenerateDockerfileExtension(Project project) {
    this.image = project.getObjects().property(String.class);
    this.buildDirectory = project.getObjects().property(String.class);
    this.jarFileName = project.getObjects().property(String.class);
    this.jvmArguments = project.getObjects().property(String.class);
    this.exposePort = project.getObjects().property(Integer.class);
    this.applyDefaults(project);
  }

  /**
   * Apply default values to the extension properties if they are not set.
   * This method is called by the constructor. It is not intended to be called by the user.
   *
   * @param project the gradle project
   */
  private void applyDefaults(Project project) {
    this.image.convention(DEFAULT_IMAGE);
    this.buildDirectory.convention(DEFAULT_BUILD_DIRECTORY);
    this.jarFileName.convention(String.format(DEFAULT_JAR_FILE_NAME_TEMPLATE, project.getName()));
    this.exposePort.convention(DEFAULT_EXPOSE_PORT);
  }
}
