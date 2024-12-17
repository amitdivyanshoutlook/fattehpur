package com.pmshree.service;

import com.pmshree.model.Attendance;
import com.pmshree.model.Student;
import com.pmshree.model.Teacher;
import com.pmshree.repository.AttendanceRepository;
import com.pmshree.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Attendance> getAttendanceByDateAndClass(LocalDate date, String className) {
        return attendanceRepository.findByDateAndClassName(date, className);
    }

    public List<Attendance> initializeAttendanceForClass(LocalDate date, String className, Teacher teacher) {
        List<Student> students = studentRepository.findByClassName(className);

        List<Attendance> existingAttendance = getAttendanceByDateAndClass(date, className);



        List<Attendance> attendanceList =students.stream().map(student -> {
           Optional<Student> studentDb = studentRepository.findById(student.getId());

            Attendance attendance = new Attendance();
            attendance.setStudent(studentDb.get());
            attendance.setDate(date);
            attendance.setClassName(className);
            attendance.setPresent(false);
            attendance.setAcademicYear(String.valueOf(date.getYear()));
            attendance.setTeacher(teacher);
            return attendance;
        }).collect(Collectors.toList());
        if (!existingAttendance.isEmpty()) {
           if(existingAttendance.get(0).getTeacher().getId() != teacher.getId())
               existingAttendance.forEach(attendance -> attendance.setTeacher(teacher));

            return existingAttendance;
        }
        else {
            return attendanceList;
        }
    }

    public void saveAttendance(List<Attendance> attendanceList) {
        attendanceRepository.saveAll(attendanceList);
    }


}