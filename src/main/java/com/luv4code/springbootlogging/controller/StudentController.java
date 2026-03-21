package com.luv4code.springbootlogging.controller;

import com.luv4code.springbootlogging.dto.StudentResponseDTO;
import com.luv4code.springbootlogging.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/all")
    public String getAllStudents(Model model){
        List<StudentResponseDTO> allStudents = studentService.getAll();
        model.addAttribute("studentList", allStudents);
        return "home";
    }
}
