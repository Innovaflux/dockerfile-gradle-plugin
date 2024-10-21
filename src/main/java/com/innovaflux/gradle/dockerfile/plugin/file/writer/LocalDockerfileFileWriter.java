package com.innovaflux.gradle.dockerfile.plugin.file.writer;

import com.google.common.base.Preconditions;
import com.innovaflux.gradle.dockerfile.plugin.file.DockerfileFile;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class LocalDockerfileFileWriter extends AbstractDockerfileFileWriter {
  private LocalDockerfileFileWriter(Writer writer) {
    super(writer);
  }

  @Override
  public void writeDockerfile(DockerfileFile dockerfileFile) throws IOException {
    this.writeln("FROM %s".formatted(dockerfileFile.getImage()));
    this.writeln("WORKDIR /app");
    this.writeln("COPY %s/%s /app/app.jar".formatted(dockerfileFile.getBuildDirectory(), dockerfileFile.getJarFilename()));
    var jvmArguments = dockerfileFile.getJvmArguments();
    if (jvmArguments != null) {
      this.writeln("ENV JAVA_OPTS \"%s\"".formatted(jvmArguments));
    }
    this.writeln("ENTRYPOINT [\"java\", \"-jar\", \"app/app.jar\"]");
    this.writeln("EXPOSE %d".formatted(dockerfileFile.getExposePort()));
  }

  private void writeln(String string) throws IOException {
    var formattedString = "%s\n".formatted(string);
    this.writer().write(formattedString);
  }

  public static LocalDockerfileFileWriter create(Writer writer) {
    Preconditions.checkNotNull(writer);
    return new LocalDockerfileFileWriter(writer);
  }

  public static LocalDockerfileFileWriter createDefault(Path dockerfilePath) throws IOException {
    var writer = Files.newBufferedWriter(dockerfilePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    return create(writer);
  }
}
