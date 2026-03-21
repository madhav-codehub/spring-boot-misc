package com.luv4code.springbootlogging.mapper;

import com.luv4code.springbootlogging.dto.StudentRequestDTO;
import com.luv4code.springbootlogging.dto.StudentResponseDTO;
import com.luv4code.springbootlogging.entity.Student;

public class StudentMapper {
    public static StudentResponseDTO toDTO(Student student) {
        return StudentResponseDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .build();
    }

    public static Student fromDTO(StudentRequestDTO request) {
        return Student.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }
}
