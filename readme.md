Protostuff Runtime generator of proto files.
====

Uses to generate proto files from protostuff runtime entity classes annotated by @Tag annotation from protostuff-annotation.

### Example

```
class User {
  @Tag(1)
  long id;
  @Tag(2)
  String name;
}
```

Will be generated to proto file:
```
message User {
  optional int64 id = 1;
  optional string name = 2;
}
```

### How to use

* Build this project by
```
mvn clean install
```
* Include this dependency to your pom file
```
<dependency>
  <groupId>net.webby.proto</groupId>
  <artifactId>protostuff-runtime-proto</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```
* Use generator API to generate proto files
```
String content = Generators.newProtoGenerator(schema).generate();
System.out.println(content);
```

### Requirements

This project designed to support protostuff version 1.0.9
