package dev.paul.dto.dtos;

import dev.paul.dto.entities.Student;

public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private int year;

    public StudentDto(Long studentId,
                      String firstName,
                      String lastName,
                      int year) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    // Entity and DTO Conversion using a constructor
    public StudentDto(Student entity) {
        this.studentId = entity.getStudentId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.year = entity.getYear();
    }

    public StudentDto() {}

    // Entity and DTO conversion using a method
    public Student toEntity() {
        Student entity = new Student();

        entity.setStudentId(this.studentId);
        entity.setFirstName(this.firstName);
        entity.setLastName(this.lastName);
        entity.setYear(this.year);

        return entity;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
