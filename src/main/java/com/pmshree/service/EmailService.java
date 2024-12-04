package com.pmshree.service;

import com.pmshree.model.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(ContactRequest request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo("your-email@example.com"); // Replace with your email
            message.setSubject("New Contact Form Submission from " + request.getName());
            message.setText("""
                Name: %s
                Email: %s
                Message: %s
                """.formatted(request.getName(), request.getEmail(), request.getMessage()));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}
