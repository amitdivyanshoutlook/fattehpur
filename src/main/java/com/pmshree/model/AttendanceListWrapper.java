package com.pmshree.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AttendanceListWrapper {
    private List<Attendance> attendanceList;




}
