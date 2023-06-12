package dev.paul.dto.conversions;

import dev.paul.dto.dtos.StudentDto;
import dev.paul.dto.entities.Student;
import dev.paul.dto.repositories.StudentRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EntityToDTO {
    private StudentRepository repository;

    public EntityToDTO(StudentRepository repository) {
        this.repository = repository;
    }

    // Convert Entity to DTO using constructor-based approach
    Student student = repository.findAllById("123L");
    StudentDto studentDto = new StudentDto(student);

    // Convert DTO to entity using the method
    public void saveEntity(){
        repository.save(studentDto.toEntity());
    }

    ModelMapper modelMapper;
    Student entity = new Student(123L, "Euni", "Wyan", 2018);
    // Example of using ModelMapper to convert an Entity to DTO
    StudentDto dto = modelMapper.map(entity, StudentDto.class);
    //Use ModelMapper to convert a DTO to Entity
    Student st = modelMapper.map(dto, Student.class);

}
