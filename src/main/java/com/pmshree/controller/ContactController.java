package com.pmshree.controller;

import com.pmshree.model.ContactRequest;
import com.pmshree.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    // Display contact form
    @GetMapping
    public String showContactForm(Model model) {
        model.addAttribute("contactRequest", new ContactRequest());
        return "contact";
    }

    // Handle form submission
    @PostMapping("/send")
    public String sendMessage(
            @Valid @ModelAttribute("contactRequest") ContactRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        // If there are validation errors, return to form
        if (result.hasErrors()) {
            return "contact";
        }

        try {
            emailService.sendEmail(request);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Thank you! Your message has been sent successfully.");
            return "redirect:/contact";
        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    "Failed to send message. Please try again later.");
            return "contact";
        }
    }
}
