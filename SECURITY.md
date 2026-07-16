# Security Policy

## Supported versions

Only the latest published release of `jmbus` is supported with security fixes.

## Reporting a vulnerability

Please **do not** open a public GitHub issue for security reports. Use GitHub's private
vulnerability reporting instead: go to the
[Security tab](https://github.com/towfiq-ul/jmbus/security) of this repository and click
"Report a vulnerability". That opens a private conversation with the maintainer, not a public
issue.

If you can't use that for some reason, open a regular issue asking for an alternative contact —
without describing the vulnerability itself — and it'll be followed up on privately.

Please include:
- The version of `jmbus` (and, if relevant, the version of the bundled `libmbus.so`) affected.
- A minimal reproduction — ideally the specific hex telegram or input that triggers the issue.
- What you observed vs. what you expected.

## Why this matters more than usual here

`jmbus` isn't pure Java — `MBusService.decodeMessage(...)` hands attacker-controllable input
(a hex-encoded telegram) to native C code via JNA, which parses it directly into memory that
Java's JNA `Structure` classes also read and write, using a layout that has to match the native
struct exactly. A mismatch between the two (wrong field type, wrong order, wrong array size)
isn't a normal Java exception — it's a silent memory-layout bug, and depending on the input, a
potential crash or memory-safety issue rather than a clean error.

That's not hypothetical: this project fixed exactly this class of bug before its first public
release (`mbus_data_variable.record` was mapped as an embedded structure instead of a pointer —
see `CHANGELOG.md`). If you find another JNA/struct-layout mismatch, a crafted telegram that
crashes the JVM, or anything else that smells like a native memory-safety issue rather than an
ordinary logic bug, please report it privately rather than as a public issue.
