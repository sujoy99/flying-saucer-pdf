package com.starter.lab.service;

import com.starter.lab.directory.StudentRepository;
import com.starter.lab.entity.Student;
import com.starter.lab.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileName = "a" + System.currentTimeMillis() + ".png";
//        student.setImageUrl(file.getOriginalFilename());
        student.setImageUrl(fileName);
        studentRepository.save(student);
//        String uploadDir = "images/" + student.getId();
        String uploadDir = "images/";
        FileUploadUtil.saveFile(uploadDir, fileName, file);
        // Save the file to the static/images directory
        // Note: You may want to add additional checks and handle exceptions here
//        file.transferTo(Path.of("src/main/resources/static/images/" + student.getImageUrl()));
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
}
