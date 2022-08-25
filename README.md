# jmbus

This is Java opensource Library for M-Bus (Meter Bus) protocol which is build from core library C libmbus.
Here is the repository of the C Libmbus Library: https://github.com/rscada/libmbus

libmbus is an open source library for the M-bus (Meter-Bus) protocol.


## Installation
1. cd `/src/main/java`
2. run `generateNativeLibAndJavaExecute.sh`
3. from `jmbus` directory run `mvn clean install`
4. copy the generated `target/jmbuslib.jar` file to your project lib
5. copy the generated `resources/libnative.so` file to your `src/main/resources` project directory