# jmbus

This is Java opensource Library for M-Bus (Meter Bus) protocol which is build from core library C libmbus.
Here is the repository of the C Libmbus Library: https://github.com/rscada/libmbus

libmbus is an open source library for the M-bus (Meter-Bus) protocol.


## Installation
* from `jmbus` directory run `mvn clean install`
* copy the generated `target/j-m-bus.jar` file to your project resource
i.e. `${main.dir}/src/main/resources/j-m-bus.jar`
* copy `resources/libmbus.so` to `${main.dir}/src/main/resources/libmbus.so`


## MAVEN POM Dependency
```
<dependency>
    <groupId>com.github.laziestcoder</groupId>
    <artifactId>j-m-bus</artifactId>
    <version><M.m.p></version>
  <!--  <scope>system</scope> -->
  <!--  <systemPath>${main.dir}/src/main/resources/j-m-bus.jar</systemPath> -->
</dependency>
```