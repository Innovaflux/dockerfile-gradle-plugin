package com.innovaflux.gradle.dockerfile.plugin.file;

import com.innovaflux.gradle.dockerfile.plugin.extension.GenerateDockerfileExtension;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public final class DockerfileFileFactory {
  public DockerfileFile createDockerfile(GenerateDockerfileExtension extension) {
    var image = extension.getImage().get();
    var buildDirectory = extension.getBuildDirectory().get();
    var jarFileName = extension.getJarFileName().get();
    var jvmArguments = extension.getJvmArguments().getOrNull();
    var exposePort = extension.getExposePort().get();
    return DockerfileFile.builder()
      .image(image)
      .buildDirectory(buildDirectory)
      .jarFilename(jarFileName)
      .jvmArguments(jvmArguments)
      .exposePort(exposePort)
      .build();
  }
}
