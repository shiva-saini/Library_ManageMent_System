package com.backendMarch.librarymanagementsystem.Controller;

import com.backendMarch.librarymanagementsystem.DTO.StudentRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Student;
import com.backendMarch.librarymanagementsystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public String addStudent(@RequestBody StudentRequestDto studentRequestDto){
       studentService.addStudent(studentRequestDto);
       return "Student has been added";

    }
    @GetMapping("/get_By_Email")
    public String getStudentByEmail(@RequestParam("email") String email){
         return studentService.findByEmail(email);
    }
    @GetMapping("/get_By_Age")
    public List<String> getStudentsByAge(@RequestParam("age") int age){
        return studentService.findByAge(age);
    }

    @PutMapping("/update_Email")
    public StudentUpdateEmailResponseDto updateMob(@RequestBody StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        return studentService.updateEmail(studentUpdateEmailRequestDto);
    }
}
