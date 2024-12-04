package com.pmshree.controller;


import com.pmshree.model.Student;
import com.pmshree.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student-management";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", new Student());
        return "students_form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "students_form";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students_form";
        }

        try {
            if (student.getId() == null) {
                studentService.createStudent(student);
                redirectAttributes.addFlashAttribute("message", "Student created successfully!");
            } else {
                studentService.updateStudent(student.getId(), student);
                redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
            }
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }

        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("message", "Student deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/students";
    }
}
