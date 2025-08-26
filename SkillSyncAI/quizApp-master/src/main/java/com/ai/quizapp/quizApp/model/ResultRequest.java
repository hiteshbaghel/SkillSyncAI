package com.ai.quizapp.quizApp.model;

import java.util.Map;

public class ResultRequest {
    private String userEmail;
    private String quizID;
    private double timeTaken;
    private Map<String, String> userAnswers;

    // Getters and setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getQuizID() { return quizID; }
    public void setQuizID(String quizId) { this.quizID = quizId; }
    public double getTimeTaken() { return timeTaken; }
    public void setTimeTaken(double timeTaken) { this.timeTaken = timeTaken; }
    public Map<String, String> getUserAnswers() { return userAnswers; }
    public void setUserAnswers(Map<String, String> userAnswers) { this.userAnswers = userAnswers; }
}