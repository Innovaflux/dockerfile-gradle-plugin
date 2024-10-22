plugins {
  idea
  signing
  `maven-publish`
  `java-gradle-plugin`
}

group = "com.innovaflux"
version = "1.0.0"

repositories {
  mavenCentral()
}

dependencies {
  annotationProcessor("org.projectlombok:lombok:1.18.34")
  compileOnly("org.projectlombok:lombok:1.18.34")
  implementation("com.google.guava:guava:33.3.1-jre")
  implementation(gradleApi())
  testImplementation(gradleTestKit())
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.2")
  testImplementation("org.junit.platform:junit-platform-commons:1.11.3")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
  withJavadocJar()
  withSourcesJar()
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
  options.release.set(21)
}

tasks.withType<Javadoc> {
  if (JavaVersion.current().isJava9Compatible) {
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

gradlePlugin {
  plugins {
    create("dockerfilePlugin") {
      id = "com.innovaflux.dockerfile-plugin"
      implementationClass =
        "com.innovaflux.gradle.dockerfile.plugin.DockerfileGradlePlugin"
    }
  }
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      groupId = project.group.toString()
      artifactId = "dockerfile-plugin"
      version = project.version.toString()
      from(components["java"])
    }
  }
  //TODO: Add maven central publishing
}

signing {
  sign(publishing.publications["mavenJava"])
}
