## Converting Entity To DTO In Spring REST API
Covers converting an Entity to a DTO and converting a 
DTO to an Entity manually or using the Model Mapper Library.

### Entity Beans Vs Data Transfer Objects(DTOs)
Entities represent an application's persistence model and are always 
internal to the application.
DTOs represent the models that an application exposes to the 
outside world.

We should not expose the internal persistence models of an 
application to the outside world. This means that controllers 
should always interact with DTOs, while the repositories (DAOs) 
should interact with the entities.

One of the benefits of keeping the DTOs and Entities separate is 
that they can change independently.

Also, having a dedicated service layer keeps the Controllers and DAOs
detached

### Entity Bean for our application
```java
package dev.paul.dto.entities;

@Entity
public class Student {
    @Id
    private Long studentId;
    private String firstName;
    private String lastName;
    private int year;
    
    // Constructor, setters and getters ommited for brevity
}    
```
### DTO 
The Data Transfer Object represents our REST service's contract.
```java
package dev.paul.dto.dtos;

public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private int year;
    
    // Constructor, setters and getters ommited
}
```

### Entity and DTO Conversion using a Constructor
A DTO class can have a constructor that instantiates itself using
the provided Entity Instance. Alternatively, an entity class can 
have a constructor that accepts a DTO instance as an argument.
#### Entity and DTO mapping using a constructor.
```java
package dev.paul.dto.dtos;

public class StudentDto {
    ...
    // Entity and DTO Conversion using a constructor
    public StudentDto(Student entity) {
        this.studentId = entity.getStudentId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.year = entity.getYear();
    }
    ...
}
```
Having constructor-based approach, we can convert an Entity to DTO
like this:
```java
package dev.paul.dto.conversions;

public class EntityToDTO {
    ...
    // Convert Entity to DTO using constructor-based approach
    Student student = repository.findAllById("123L");
    StudentDto studentDto = new StudentDto(student);
    ...
}
```
### Entity and DTO Conversion using a Method
An entity or a DTO can transform into the other types by 
providing a conversion method.
#### Entity and DTO mapping using a Dedicated Method
```java
package dev.paul.dto.dtos;

public class StudentDto {
    ...
    // Entity and DTO conversion using a method
    public Student toEntity() {
        Student entity = new Student();

        entity.setStudentId(this.studentId);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setYear(this.year);

        return entity;
    }
    ...
}
```
The conversion method can convert a DTO instance into an entity.
```java
package dev.paul.dto.conversions;

public class EntityToDto {
    // Convert DTO to entity using the method
    public void saveEntity() {
        repository.save(studentDto.toEntity());
    }
}
```
### Entity and DTO Conversion using a Dedicated Converter
The previous examples of using a constructor or a method for doing
Entity to DTO conversions are simple. However, the class defining 
such a constructor or method depends on the other and will change
if the other class changes.

To keep both Entity  and DTO classes independent and detached, 
create a dedicated converter class that handles the mapping 
between the Entity and the DTO fields.
#### Example of Entity and DTO Conversion using a Dedicated Mapper Class
```java
package dev.paul.dto.mapper;

public class StudentMapper {

    // Entity and DTO Conversion using a Dedicated Mapper class
    
    public StudentDto toDto(Student entity) {
        StudentDto dto = new StudentDto();

        dto.setStudentId(entity.getStudentId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setYear(entity.getYear());

        return dto;
    }

    public Student toEntity(StudentDto dto) {
        Student entity = new Student();
        entity.setStudentId(dto.getStudentId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setYear(dto.getYear());

        return entity;
    }
}
```
### Entity and DTO Conversion using Model Mapper
The manual ways of conversions are suitable for small classes. 
However, the manual way is time-consuming and error-prone when
we have large beans or many DTO and Entity pairs. To avoid that,
we can use the [ModelMapper library](http://modelmapper.org/), which uses reflections to 
convert between classes having the same fields. Let's create a 
ModelMapper implementation for an Entity to DTO Conversion.

First, we need to add the [model mapper dependency](https://mvnrepository.com/artifact/org.modelmapper/modelmapper) to our 
project's pom.xml.
```xml
    <!-- https://mvnrepository.com/artifact/org.modelmapper/modelmapper -->
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.1.1</version>
    </dependency>
```
Next, we can create a @Bean factory method to create a *ModelMapper* 
instance. This way, the model mapper instance will  be available
for injection on the application level. 
```java
package dev.paul.dto.mapper;

public class MapperBean {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```
To do the Entity and DTO conversion, we can use its *map()* method 
that takes the source instance and the target type.
#### Using ModelMapper to convert an Entity to DTO.
```java
package dev.paul.dto.conversions;

public class EntityToDTO {
    ...
    // Example of using ModelMapper to convert an Entity to DTO
    StudentDto dto = modelMapper.map(entity, StudentDto.class);
    
    // Example using ModelMapper to convert a DTO to Entity
    Student st = modelMapper.map(dto, Student.class);
    
}
```
### Summary
This tutorial explained several ways of Converting Entities to
DTOs and Converting DTOs to Entities. We learned that an 
Entity bean or DTO could have a constructor or a dedicated 
conversion method to do the Entity and DTO conversion. Also, 
we can have a standalone mapper class that performs the mapping 
but keep the Entity and DTO detached.

In the last section, we learned that ModelMapper, an open-source
library, provides an abstraction to map two entities with the 
same fields.

### This project is sourced from [this article](https://www.amitph.com/spring-entity-to-dto/). All rights reserved to their respective owners.

