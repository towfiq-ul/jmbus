# jmbus

This is Java opensource Library for M-Bus (Meter Bus) protocol which is build from core library C libmbus.
Here is the repository of the C Libmbus Library: https://github.com/rscada/libmbus

libmbus is an open source library for the M-bus (Meter-Bus) protocol.


## Installation

jmbus can be built with either Maven or Gradle — pick whichever your project already uses.

### Maven
* from `jmbus` directory run `mvn clean install`
* copy the generated `target/jmbus.jar` file to your project resource
i.e. `${main.dir}/src/main/resources/jmbus.jar`
* copy `resources/libmbus.so` to `${main.dir}/src/main/resources/libmbus.so`

### Gradle
* from `jmbus` directory run `./gradlew build` (or `./gradlew publishToMavenLocal` to install it into `~/.m2` for other projects to consume)
* copy `resources/libmbus.so` to your project's resources, same as above

Either way, `libmbus.so` must end up as an actual file on the consuming application's classpath (e.g. `src/main/resources/libmbus.so`) — it is loaded via `ResourceUtils.getFile(...)` at runtime, which does not work for resources packed inside a jar.

## MAVEN POM Dependency

Once published to Maven Central (see [PUBLISHING.md](PUBLISHING.md)):
```
<dependency>
    <groupId>io.github.towfiq-ul</groupId>
    <artifactId>jmbus</artifactId>
    <version><M.m.p></version>
</dependency>
```

## GRADLE Dependency

Once published to Maven Central (see [PUBLISHING.md](PUBLISHING.md)):
```groovy
dependencies {
    implementation 'io.github.towfiq-ul:jmbus:<M.m.p>'
}
```