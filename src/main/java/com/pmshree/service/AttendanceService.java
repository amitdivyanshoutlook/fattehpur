package com.pmshree.service;

import com.pmshree.model.Attendance;
import com.pmshree.model.AttendanceStats;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import com.pmshree.model.AttendanceStats;
import java.util.HashMap;
import java.util.Map;
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

    private List<AttendanceStats> getAttendanceStatsForDateRange(LocalDate startDate, LocalDate endDate, String className) {
        List<Attendance> attendanceList = attendanceRepository.findByDateBetweenAndClassName(startDate, endDate, className);
        return calculateAttendanceStats(attendanceList);
    }

    public List<AttendanceStats> getWeeklyAttendance(LocalDate date, String className) {
        LocalDate weekStart = date.minusDays(date.getDayOfWeek().getValue() - 1);
        LocalDate weekEnd = weekStart.plusDays(6);
        return getAttendanceStatsForDateRange(weekStart, weekEnd, className);
    }

    public List<AttendanceStats> getMonthlyAttendance(LocalDate date, String className) {
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.withDayOfMonth(date.lengthOfMonth());
        return getAttendanceStatsForDateRange(monthStart, monthEnd, className);
    }

    public List<AttendanceStats> getYearlyAttendance(String academicYear, String className) {
        String[] years = academicYear.split("-");
        LocalDate yearStart = LocalDate.of(Integer.parseInt("20" + years[0]), 6, 1);
        LocalDate yearEnd = LocalDate.of(Integer.parseInt("20" + years[1]), 5, 31);
        return getAttendanceStatsForDateRange(yearStart, yearEnd, className);
    }

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

    private List<AttendanceStats> calculateAttendanceStats(List<Attendance> attendanceList) {
        Map<Long, AttendanceStats> statsMap = new HashMap<>();
        
        for (Attendance attendance : attendanceList) {
            Long studentId = attendance.getStudent().getId();
            AttendanceStats stats = statsMap.computeIfAbsent(studentId,
                    k -> new AttendanceStats(attendance.getStudent()));
            
            stats.incrementTotalDays();
            if (attendance.isPresent()) {
                stats.incrementPresentDays();
            }
        }
        
        return new ArrayList<>(statsMap.values());
    }

    public void saveAttendance(List<Attendance> attendanceList) {
        attendanceRepository.saveAll(attendanceList);
    }


}