package com.luv4code.springbootlogging.service.impl;

import com.luv4code.springbootlogging.dto.StudentRequestDTO;
import com.luv4code.springbootlogging.dto.StudentResponseDTO;
import com.luv4code.springbootlogging.entity.Student;
import com.luv4code.springbootlogging.exception.StudentExistsException;
import com.luv4code.springbootlogging.exception.StudentNotFoundException;
import com.luv4code.springbootlogging.mapper.StudentMapper;
import com.luv4code.springbootlogging.repository.StudentRepository;
import com.luv4code.springbootlogging.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String CLASS_NAME = StudentService.class.getSimpleName();

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDTO getStudentById(Long studentId) {
        final String METHOD = "getStudentById";
        log.info("{}.{} - START -studentId={}", CLASS_NAME, METHOD, studentId);
        Student student = null;
        try {
            student = studentRepository.findById(studentId).orElseThrow(
                    () -> new StudentNotFoundException("Student not found with the ID: " + studentId)
            );
            log.info("{}.{} - SUCCESS -studentId={}, name={}", CLASS_NAME, METHOD, studentId, student.getFirstName());
            return StudentMapper.toDTO(student);
        } catch (StudentNotFoundException e) {
            log.info("{}.{} - FAILED -studentId={}, message={}", CLASS_NAME, METHOD, studentId, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StudentResponseDTO> getAll() {
        final String METHOD = "getAll";
        log.info("{}.{} - START", CLASS_NAME, METHOD);
        try {
            List<Student> students = studentRepository.findAll();
            List<StudentResponseDTO> dtos = students.stream()
                    .map(StudentMapper::toDTO)
                    .toList();
            log.info("{}.{} - SUCCESS - found {} students", CLASS_NAME, METHOD, dtos.size());
            return dtos;
        } catch (Exception e) {
            log.error("{}.{} - FAILED - message={}", CLASS_NAME, METHOD, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        final String METHOD = "createStudent";
        log.info("{}.{} - START - email={}", CLASS_NAME, METHOD, requestDTO.email());
        try {
            if (studentRepository.findByEmail(requestDTO.email()).isPresent()) {
                log.warn("{}.{} - EMAIL_ALREADY_EXISTS - email={}",
                        CLASS_NAME, METHOD, requestDTO.email());
                throw new StudentExistsException("Student already existed with the given email.");
            }

            Student student = StudentMapper.fromDTO(requestDTO);
            Student saved = studentRepository.save(student);

            log.info("{}.{} - SUCCESS - id={}, email={}, name={}", CLASS_NAME, METHOD, saved.getId(), saved.getEmail(), saved.getFirstName());

            return StudentMapper.toDTO(saved);
        } catch (StudentExistsException e) {
            log.info("{}.{} - FAILED - email={}, message={}",
                    CLASS_NAME, METHOD, requestDTO.email(), e.getMessage());
            throw e;   // rethrow business exception as-is

        } catch (Exception e) {
            log.error("{}.{} - FAILED - email={}, message={}",
                    CLASS_NAME, METHOD, requestDTO.email(), e.getMessage(), e);
            throw new RuntimeException("Failed to create student", e);
        }

    }

    @Override
    public StudentResponseDTO updateStudent(StudentRequestDTO requestDTO, Long studentId) {
        final String METHOD = "updateStudent";
        log.info("{}.{} - START - studentId={}, email={}", CLASS_NAME, METHOD, studentId, requestDTO.email());

        try {
            Student existedStudent = studentRepository.findById(studentId).orElseThrow(
                    () -> new StudentNotFoundException("Student not found with the given ID: " + studentId)
            );
            studentRepository.findByEmail(requestDTO.email()).ifPresent(student -> {
                if (!student.getId().equals(studentId)) {
                    throw new StudentExistsException("Student already existed with the given email.");
                }
            });
            existedStudent.setFirstName(requestDTO.firstName());
            existedStudent.setLastName(requestDTO.lastName());
            existedStudent.setEmail(requestDTO.email());

            Student savedStudent = studentRepository.save(existedStudent);

            log.info("{}.{} - SUCCESS - studentId={}, email={}, name={}", CLASS_NAME, METHOD, savedStudent.getId(), savedStudent.getEmail(), savedStudent.getFirstName());

            return StudentMapper.toDTO(savedStudent);
        } catch (StudentNotFoundException | StudentExistsException e) {
            log.info("{}.{} - FAILED - studentId={}, email={}, message={}",
                    CLASS_NAME, METHOD, studentId, requestDTO.email(), e.getMessage());
            throw e;  // rethrow known business exceptions as-is

        } catch (Exception e) {
            log.error("{}.{} - FAILED - studentId={}, email={}, message={}",
                    CLASS_NAME, METHOD, studentId, requestDTO.email(), e.getMessage(), e);
            throw new RuntimeException("Failed to update student", e);
        }
    }

    @Override
    public void deleteStudent(Long studentId) {
        final String METHOD = "deleteStudent";
        log.info("{}.{} - START - studentId={}", CLASS_NAME, METHOD, studentId);

        try {
            Student student = studentRepository.findById(studentId).orElseThrow(
                    () -> new StudentNotFoundException("Student not found with the given ID: " + studentId)
            );

            String studentName = student.getFirstName() + " " + student.getLastName();
            String studentEmail = student.getEmail();

            studentRepository.deleteById(studentId);

            log.info("{}.{} - SUCCESS - studentId={}, name={}, email={}",
                    CLASS_NAME, METHOD, studentId, studentName, studentEmail);
        } catch (StudentNotFoundException e) {
            log.info("{}.{} - FAILED - studentId={}, message={}",
                    CLASS_NAME, METHOD, studentId, e.getMessage());
            throw e;  // rethrow business exception

        } catch (Exception e) {
            log.error("{}.{} - FAILED - studentId={}, message={}",
                    CLASS_NAME, METHOD, studentId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete student with id: " + studentId, e);
        }
    }
}
