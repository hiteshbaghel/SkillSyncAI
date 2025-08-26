package com.ai.quizapp.quizApp.model;

public class TestRequest {
    private String subjectName;
    private String difficulty;
    private int numberOfQuestions;

    // Getters and setters
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public int getNumberOfQuestions() { return numberOfQuestions; }
    public void setNumberOfQuestions(int numberOfQuestions) { this.numberOfQuestions = numberOfQuestions; }
}