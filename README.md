# jmbus

This is Java opensource Library for M-Bus (Meter Bus) protocol which is build from core library C libmbus.
Here is the repository of the C Libmbus Library: https://github.com/rscada/libmbus

libmbus is an open source library for the M-bus (Meter-Bus) protocol.

## Credits

`jmbus` is a JNA wrapper around [libmbus](https://github.com/rscada/libmbus), and bundles a
compiled copy of it (`src/main/resources/libmbus.so`) in every build/distribution. All credit
for the M-Bus protocol implementation itself belongs to the libmbus project:

* **Project / team:** [rSCADA](https://github.com/rscada) — https://github.com/rscada/libmbus
* **Copyright:** © 2010-2012, Raditex Control AB
* **License:** BSD 3-Clause (see libmbus's own
  [LICENSE](https://github.com/rscada/libmbus/blob/master/LICENSE) and
  [COPYING](https://github.com/rscada/libmbus/blob/master/COPYING) files)

libmbus's `COPYING` file asks that "due credit ... be given to rSCADA and Raditex Control in
derivative work based on the libmbus library" — this section is that credit, and satisfies the
BSD-3-Clause requirement to reproduce libmbus's copyright notice alongside binary
redistributions of it.

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

## Usage

The entry point is `MBusService` (`io.github.towfiqul.jmbus.service.MBusService`), implemented
by `MBusServiceImpl`. In a Spring app, `MBusServiceImpl` is already annotated `@Service`, so you
can just `@Autowired` it. Outside Spring, construct it directly — `new MBusServiceImpl()` —
which loads `libmbus.so` once, at construction time.

```java
MBusService mBusService = new MBusServiceImpl();

String hexTelegram = "68 4D 4D 68 08 ..."; // a raw M-Bus telegram, as hex
JsonNode result = mBusService.decodeMessage(hexTelegram);

System.out.println(result.toString());
```

That produces structured JSON describing the meter and its readings, for example:

```json
{
  "SlaveInformation": {
    "Id": "11120895",
    "Manufacturer": "EDC",
    "Medium": "Heat: Outlet",
    "AccessNumber": "23"
  },
  "DataRecord": [
    {
      "id": "0",
      "Function": "Instantaneous value",
      "Unit": "Energy (kWh)",
      "Value": "35"
    }
  ]
}
```

There's a second overload for the common case where the hex telegram is one field inside a
larger JSON payload you already have — e.g. a message coming off a queue that carries the raw
telegram alongside metadata you don't want to lose:

```java
// dataNode = {"deviceId": "abc123", "telegram": "68 4D 4D 68 08 ..."}
JsonNode updated = mBusService.decodeMessage(dataNode, "telegram");
// updated = {"deviceId": "abc123", "telegram": { ...decoded JSON... }}
```

It decodes the hex value at the given field name and replaces it in place, leaving the rest of
`dataNode` untouched.

**Runtime requirement:** `libmbus.so` must exist as an actual file on the classpath (e.g.
`src/main/resources/libmbus.so` in your own project — see Installation above), not just be
packed inside a jar. It's loaded via Spring's `ResourceUtils.getFile(...)`, which needs a real
filesystem path. If construction fails, check the log line `Libmbus Path: ...` that
`MBusServiceImpl` prints on startup — an exception right after it usually means that path isn't
resolving to a real file.