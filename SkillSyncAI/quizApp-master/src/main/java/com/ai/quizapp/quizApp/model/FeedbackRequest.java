package com.ai.quizapp.quizApp.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FeedbackRequest {

    private String fid; // Optional field for updates; not required for submission

    @NotBlank(message = "User email cannot be empty")
    @Size(max = 50, message = "User email cannot exceed 50 characters")
    private String userEmail;

//    @NotBlank(message = "User name cannot be empty")
//    @Size(max = 50, message = "User name cannot exceed 50 characters")
//    private String userName;

    @NotBlank(message = "Feedback cannot be empty")
    @Size(max = 1000, message = "Feedback cannot exceed 1000 characters")
    private String feedback;

    // Getters, setters, constructors
    public String getFid() { return fid; }
    public void setFid(String fid) { this.fid = fid; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
//    public String getUserName() { return userName; }
//    public void setUserName(String userName) { this.userName = userName; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}