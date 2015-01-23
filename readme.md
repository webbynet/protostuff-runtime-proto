Protostuff runtime generator for .proto files
====

Uses protostuff annotations in POJOs to generate proto files. Can be usefull to understand how protostuff actually serializes Java POJOs and to build cross platform applications where source of the protocol are protostuff entities.

### Example

For POJO:
```
class User {
  @Tag(1)
  long id;
  @Tag(2)
  String name;
}
```

Will be generated a .proto file:
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

This project is designed to support protostuff version 1.0.9. All generated files are using protostuff-default.proto import, that is located in src/main/resources/
