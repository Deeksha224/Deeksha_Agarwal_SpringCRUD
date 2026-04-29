package com.example.studentmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id);
    }

    public Student update(Student student) {
        studentRepository.update(student);
        return student;
    }

    public void deleteById(Integer id) {
        studentRepository.deleteById(id);
    }
}