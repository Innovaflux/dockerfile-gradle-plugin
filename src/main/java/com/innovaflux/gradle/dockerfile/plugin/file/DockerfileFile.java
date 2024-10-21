package com.innovaflux.gradle.dockerfile.plugin.file;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public final class DockerfileFile {
  private final String image;
  private final String buildDirectory;
  private final String jarFilename;
  private final String jvmArguments;
  private final int exposePort;
}
