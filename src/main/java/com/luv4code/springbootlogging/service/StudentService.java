package com.luv4code.springbootlogging.service;

import com.luv4code.springbootlogging.dto.StudentRequestDTO;
import com.luv4code.springbootlogging.dto.StudentResponseDTO;

import java.util.List;

public interface StudentService {
    public StudentResponseDTO getStudentById(Long studentId);
    public List<StudentResponseDTO> getAll();
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO);
    public StudentResponseDTO updateStudent(StudentRequestDTO requestDTO, Long studentId);
    public void deleteStudent(Long studentId);
}
