package com.innovaflux.gradle.dockerfile.plugin.file.writer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Writer;

@Getter(value = AccessLevel.PROTECTED)
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDockerfileFileWriter implements DockerfileFileWriter {
  private final Writer writer;

  @Override
  public void close() throws Exception {
    this.writer.close();
  }
}
