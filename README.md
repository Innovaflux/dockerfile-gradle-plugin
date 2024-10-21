# dockerfile-gradle-plugin

Gradle plugin to generate a Dockerfile. If this plugin is applied to the project
it creates a task to generate the Dockerfile.
The task is executed automatically when the gradle build is executed.

## Usage

Add the plugin to the plugins block of your build script:

```kotlin
plugins {
    id("com.innovaflux.dockerfile-plugin")
}
```

## Properties

| Property       | Description                      | Default value                 |
|----------------|----------------------------------|-------------------------------|
| image          | The docker image to use          | eclipse-temurin:21-jdk-alpine |
| buildDirectory | The path to the build directory  | build/libs                    |
| jarFileName    | The name of the jar file         | project.name + .jar           |
| jvmArguments   | The arguments to pass to the JVM | None                          |
| exposePort     | The port to expose               | 8080                          |

## Configuration example

```kotlin
plugins {
    id("com.innovaflux.dockerfile-plugin")
}

dockerfileConfiguration {
    image.set("eclipse-temurin:21-jdk-alpine")
    buildDirectory.set("build")
    jarFileName.set("application.jar")
    jvmArguments.set("-Xmx512m")
    exposePort.set(8000)
}
```

## Contributing

Contributions are welcome! Please read the [contribution guidelines](CONTRIBUTING.md) before getting started.

### Contributors

<!-- readme: collaborators,contributors -start -->
<!-- readme: collaborators,contributors -end -->
