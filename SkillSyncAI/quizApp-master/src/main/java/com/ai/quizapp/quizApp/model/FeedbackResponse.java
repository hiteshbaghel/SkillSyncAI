package com.ai.quizapp.quizApp.model;

import java.time.LocalDateTime;

public class FeedbackResponse {
    private String fid; // Added feedback id
    private String userEmail;
//    private String userName;
    private LocalDateTime dateTime;
    private String feedback;

    // Constructor
    public FeedbackResponse(String fid, String userEmail,  LocalDateTime dateTime, String feedback) {
        this.fid = fid;
        this.userEmail = userEmail;
//        this.userName = userName; in method String userName,
        this.dateTime = dateTime;
        this.feedback = feedback;
    }

    // Getters and Setters
    public String getFid() { return fid; }
    public void setFid(String fid) { this.fid = fid; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
//    public String getUserName() { return userName; }
//    public void setUserName(String userName) { this.userName = userName; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}