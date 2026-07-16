# Contributing to jmbus

## Getting set up

jmbus builds with either Maven or Gradle — you don't need both, but a change to one build file
should generally be mirrored in the other (see below).

```
mvn clean install       # or
./gradlew build
```

Both run the test suite, which exercises the real native `libmbus.so` bundled at
`src/main/resources/libmbus.so` — there's no mocking of the native layer, so a passing test
means the JNA bindings actually round-tripped through the C library correctly.

Run a single test:
```
mvn test -Dtest=MBusServiceTest#decodeMessage        # Maven
./gradlew test --tests "*.MBusServiceTest.decodeMessage"   # Gradle
```

## Branching and commits

This repo follows a git-flow-style layout: `develop` is the integration branch, work happens on
`feature/<name>` branches off `develop`, and releases are cut from `release/<version>` branches
tagged `vX.Y.Z`. Prefix commit subjects with the kind of change, e.g. `feat:`, `fix:`, `docs:`.

## Where things live

- `io.github.towfiqul.jmbus.mbus` — JNA bindings (`LibMbus` + the `mbus_*` `Structure` classes)
  that mirror the C structs in [libmbus](https://github.com/rscada/libmbus)'s
  `mbus/mbus-protocol.h` and `mbus-protocol-aux.h` byte-for-byte, via `@FieldOrder`.
- `io.github.towfiqul.jmbus.service` — the public `MBusService` API and its implementation.

## Changing the JNA structures

If you touch any `mbus_*` class, cross-check the field list, order, and types against the
upstream C struct in `libmbus`'s headers before opening a PR. A mismatch doesn't fail to
compile — it silently misreads native memory. In particular:

- A C `struct *` field must be mapped as `Pointer` (or a `.ByReference` type), never as an
  embedded `Structure` field — embedding reserves space for the whole nested struct instead of
  one pointer-width slot, shifting every field that follows it.
- A C `unsigned char *` field is a raw byte buffer, not necessarily null-terminated text — map
  it as `Pointer`, not `String`.
- Fixed-size arrays (`byte[N]`) must match the C `#define`d size exactly.

Add a test that reads back a field you've changed (not just that decoding doesn't throw) —
`MBusServiceTest.mbusDataVariableRecordCountMatchesLibmbus` is an example of a test that would
have caught a layout bug the black-box hex-to-JSON test alone didn't.

## Pull requests

- Keep `pom.xml` and `build.gradle` in sync (dependency versions, Java release level,
  coordinates) — they're independent build definitions for the same sources.
- Run both `mvn clean install` and `./gradlew build` before submitting.
- See [PUBLISHING.md](PUBLISHING.md) if your change touches release/publishing configuration.

## License

By contributing, you agree your contribution is licensed under this project's
[BSD-3-Clause license](LICENSE).

`src/main/resources/libmbus.so` is a compiled binary of a third-party library
([libmbus](https://github.com/rscada/libmbus), © 2010-2012 Raditex Control AB, BSD-3-Clause),
not jmbus's own code. If you touch that file or how it's bundled, keep the third-party
notice in [LICENSE](LICENSE) and the credit in [README.md](README.md) intact and up to date —
don't remove or fold it into jmbus's own copyright line.
