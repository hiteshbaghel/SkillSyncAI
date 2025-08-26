package com.ai.quizapp.quizApp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ReportCardRequest {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}