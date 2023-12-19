package com.starter.lab.controller;

import com.starter.lab.entity.Student;
import com.starter.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public String viewStudentDetails(@PathVariable("id") Integer id, Model model) {
        Student student = studentService.getStudent(Long.valueOf(id));
        model.addAttribute("student", student);
        return "studentDetails";
    }

    @PostMapping("/upload")
    public String uploadStudentImage(@RequestParam("file") MultipartFile file, Student student) throws IOException {
        studentService.saveStudent(student, file);
        System.out.println(student.getId());
        return "redirect:/students/" + student.getId();
    }

    @GetMapping("/form")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "studentForm";
    }
}
