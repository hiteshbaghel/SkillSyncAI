package com.ai.quizapp.quizApp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_feedback")
public class Feedback {

    @Id
    @Column(name = "fid", length = 45)
    private String fid;

    @Column(name = "feedbackID", length = 45) // Added feedbackID field
    private String feedbackID;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "userEmail")
    private UserInfo user;



    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Feedback() {
        this.fid = java.util.UUID.randomUUID().toString();
        this.feedbackID = java.util.UUID.randomUUID().toString(); // Generate feedbackID
        this.createdAt = LocalDateTime.now();
    }

    public Feedback(UserInfo user,  String feedback) {
        this();
        this.user = user;

        this.feedback = feedback;
    }

    // Getters and Setters
    public String getFid() { return fid; }
    public void setFid(String fid) { this.fid = fid; }

    public String getFeedbackID() { return feedbackID; }
    public void setFeedbackID(String feedbackID) { this.feedbackID = feedbackID; }

    public UserInfo getUser() { return user; }
    public void setUser(UserInfo user) { this.user = user; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}