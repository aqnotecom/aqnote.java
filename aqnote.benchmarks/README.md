Setting up the benchmarking project.The following command will generate the new JMH-driven project in test folder:
```java
$ mvn archetype:generate \
          -DinteractiveMode=false \
          -DarchetypeGroupId=org.openjdk.jmh \
          -DarchetypeArtifactId=jmh-java-benchmark-archetype \
          -DgroupId=org.sample \
          -DartifactId=test \
          -Dversion=1.0
```
If you want to benchmark an alternative JVM language, use another archetype artifact ID from the list of existing ones, it usually amounts to replacing java to another language in the artifact ID given below. Using alternative archetypes may require additional changes in the build configuration, see the pom.xml in the generated project.


Building the benchmarks. After the project is generated, you can build it with the following Maven command:
```java
$ cd test/
$ mvn clean install
```

Running the benchmarks. After the build is done, you will get the self-contained executable JAR, which holds your benchmark, and all essential JMH infrastructure code:
```java
$ java -jar target/benchmarks.jar
```
Run with -h to see the command line options available.