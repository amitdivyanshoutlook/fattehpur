package com.pmshree.service;


import com.pmshree.exception.ResourceNotFoundException;
import com.pmshree.model.Student;
import com.pmshree.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student createStudent(Student student) {

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);

        student.setFirstName(studentDetails.getFirstName());


        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }


}
