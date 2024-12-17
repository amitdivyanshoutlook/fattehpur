package com.pmshree.controller;

import com.pmshree.model.Attendance;
import com.pmshree.model.AttendanceListWrapper;
import com.pmshree.model.Teacher;
import com.pmshree.repository.StudentRepository;
import com.pmshree.service.AttendanceService;
import com.pmshree.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentRepository studentRepository;


    @GetMapping
    public String showAttendancePage(Model model) {

        model.addAttribute("teachers", teacherService.getAllTeachers());

        model.addAttribute("classes", studentRepository.findAllUniqueClasses());
        model.addAttribute("today", LocalDate.now());
        return "attendance/select-teacher";
    }

    @GetMapping("/mark")
    public String showMarkAttendance(@RequestParam("teacherId") Long teacherId,
                                     @RequestParam("className") String className,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     Model model) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        List<Attendance> attendanceList = attendanceService.initializeAttendanceForClass(date, className,teacher);
       AttendanceListWrapper attendanceListWrapper = AttendanceListWrapper.builder()
                        .attendanceList(attendanceList)
                .build();
        model.addAttribute("attendanceListWrapper", attendanceListWrapper); // Wrapping the list
        model.addAttribute("date", date);

        model.addAttribute("className", className);
        model.addAttribute("teacher", teacher);
        return "attendance/mark-attendance";
    }

    @PostMapping("/save")
    public String saveAttendance(@ModelAttribute("attendanceListWrapper") AttendanceListWrapper attendanceListWrapper) {

        attendanceService.saveAttendance(attendanceListWrapper.getAttendanceList());


        return "redirect:/attendance?success";
    }
}