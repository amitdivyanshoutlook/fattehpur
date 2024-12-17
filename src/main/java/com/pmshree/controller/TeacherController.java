package com.pmshree.controller;


import com.pmshree.model.Teacher;
import com.pmshree.repository.StudentRepository;
import com.pmshree.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;


@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public String listTeachers(Model model) {

        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("classes", studentRepository.findAllUniqueClasses());

        model.addAttribute("today", LocalDate.now());
        return "teacher-management";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teachers_form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("teacher", teacherService.getTeacherById(id));
        return "teachers_form";
    }

    @PostMapping("/save")
    public String saveTeacher(@Valid @ModelAttribute Teacher teacher,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "teachers_form";
        }

        try {
            if (teacher.getId() == null) {
                teacherService.saveTeacher(teacher);
                redirectAttributes.addFlashAttribute("message", "Teacher created successfully!");
            } else {
               // teacherService.updateStudent(student.getId(), student);
                redirectAttributes.addFlashAttribute("message", "Teacher updated successfully!");
            }
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }

        return "redirect:/teachers";
    }



    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(id);
            redirectAttributes.addFlashAttribute("message", "Teacher deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/teachers";
    }
}
