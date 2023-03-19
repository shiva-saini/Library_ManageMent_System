package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.StudentRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Student;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
        public void addStudent(StudentRequestDto studentRequestDto){
            //create student from dto
            Student student = new Student();
            student.setName(studentRequestDto.getName());
            student.setEmail(studentRequestDto.getEmail());
            student.setAge(studentRequestDto.getAge());
            student.setDepartment(studentRequestDto.getDepartment());

            //create card object
            LibraryCard card = new LibraryCard();
            card.setStatus(CardStatus.ACTIVATED);

            // set card in student
            student.setCard(card);
            //set student in card
            card.setStudent(student); // if u dont do this studentId will be null in db;
            studentRepository.save(student);

            // check

            studentRepository.save(student); // will save student and card
        }

        public String findByEmail(String email){
           Student student =  studentRepository.findByEmail(email);
           return student.getName();
        }

        public List<String> findByAge(int age){
            List<Student> list = studentRepository.findByAge(age);
            List<String> students = new ArrayList<>();
            for(Student student:list){
                students.add(student.getName());
            }
            return students;
        }

        public StudentUpdateEmailResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
            Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
            student.setEmail(studentUpdateEmailRequestDto.getEmail());
            Student updatedStudent = studentRepository.save(student);
            StudentUpdateEmailResponseDto studentUpdateEmailResponseDto = new StudentUpdateEmailResponseDto();
            studentUpdateEmailResponseDto.setId(updatedStudent.getId());
            studentUpdateEmailResponseDto.setEmail(updatedStudent.getEmail());
            studentUpdateEmailResponseDto.setName(updatedStudent.getName());
            return studentUpdateEmailResponseDto;
        }
}
