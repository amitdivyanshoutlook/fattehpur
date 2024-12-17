package com.pmshree.controller;

import com.pmshree.model.Attendance;
import com.pmshree.model.AttendanceStats;
import com.pmshree.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance-dashboard")
public class AttendanceDashboardController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public String showDashboard(Model model) {
        // Add current year and available academic years
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("academicYears", List.of("2023-24", "2022-23", "2021-22"));
        return "attendance/dashboard";
    }

    @GetMapping("/view")
    public String viewAttendance(
            @RequestParam String academicYear,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam String className,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String viewType,
            Model model) {
        
        List<?> attendanceData;
        if (viewType == null) {
            viewType = "daily";
        }
        
        switch (viewType) {
            case "weekly":
                attendanceData = attendanceService.getWeeklyAttendance(date, className);
                break;
            case "monthly":
                attendanceData = attendanceService.getMonthlyAttendance(date, className);
                break;
            case "yearly":
                attendanceData = attendanceService.getYearlyAttendance(academicYear, className);
                break;
            default:
                attendanceData = attendanceService.getAttendanceByDateAndClass(date, className);
                break;
        }
        
        model.addAttribute("attendanceData", attendanceData);
        model.addAttribute("selectedDate", date);
        model.addAttribute("selectedClass", className);
        model.addAttribute("academicYear", academicYear);
        model.addAttribute("viewType", viewType);
        
        return "attendance/dashboard-view";
    }
}