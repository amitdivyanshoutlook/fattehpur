package com.pmshree.repository;

import com.pmshree.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByDateAndClassName(LocalDate date, String className);
    List<Attendance> findByDateBetweenAndClassName(LocalDate startDate, LocalDate endDate, String className);
}