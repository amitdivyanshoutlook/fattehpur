package com.pmshree.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String gender;

    private String dob;
    private String admissionDate;
    private String srNumber;
    private String uniqueNumber;
    private String motherName;
    private String fatherName;
    private String ifscCode;
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String aadharNumber;
    private String className;

}
