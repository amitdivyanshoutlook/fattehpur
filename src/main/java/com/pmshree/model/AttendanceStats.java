package com.pmshree.model;

import lombok.Data;

@Data
public class AttendanceStats {
    private Student student;
    private int totalDays;
    private int presentDays;
    private double attendancePercentage;

    public AttendanceStats(Student student) {
        this.student = student;
        this.totalDays = 0;
        this.presentDays = 0;
        this.attendancePercentage = 0.0;
    }

    public void incrementTotalDays() {
        this.totalDays++;
        calculatePercentage();
    }

    public void incrementPresentDays() {
        this.presentDays++;
        calculatePercentage();
    }

    private void calculatePercentage() {
        if (totalDays > 0) {
            this.attendancePercentage = (double) presentDays / totalDays * 100;
        }
    }
}