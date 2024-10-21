package com.innovaflux.gradle.dockerfile.plugin.file.writer;

import com.innovaflux.gradle.dockerfile.plugin.file.DockerfileFile;

import java.io.IOException;

public interface DockerfileFileWriter extends AutoCloseable {
  void writeDockerfile(DockerfileFile dockerfileFile) throws IOException;
}
