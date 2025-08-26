package com.ai.quizapp.quizApp.model;

import org.springframework.stereotype.Component;


public class LoginRequest {
    private String email;
    private String profileUrl;
    private String name;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getProfileUrl() { return profileUrl; }
    public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}