package dev.paul.dto.mapper;

import dev.paul.dto.dtos.StudentDto;
import dev.paul.dto.entities.Student;

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
