package com.starter.lab.service;

import com.starter.lab.directory.StudentRepository;
import com.starter.lab.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveStudent(Student student, MultipartFile file) throws IOException {
        String fileName = "a" + System.currentTimeMillis() + ".png";
//        student.setImageUrl(file.getOriginalFilename());
        student.setImageUrl(fileName);
        studentRepository.save(student);

        // Save the file to the static/images directory
        // Note: You may want to add additional checks and handle exceptions here
        file.transferTo(Path.of("src/main/resources/static/images/" + student.getImageUrl()));
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
}
