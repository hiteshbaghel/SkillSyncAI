package com.ai.quizapp.quizApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Table(name = "QUIZ_DATA")
public class QuizData {
    @Id
    private String quizID; // Primary key, matching the database column name

    private String subjectName;
    private String difficultyLevel;
    private int numberOfQuestions;
    private LocalDateTime dateTime; // New attribute for quiz generation time

    // Getters, setters, constructors
    public QuizData() {}

    public String getQuizID() { return quizID; }
    public void setQuizID(String quizID) { this.quizID = quizID; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public int getNumberOfQuestions() { return numberOfQuestions; }
    public void setNumberOfQuestions(int numberOfQuestions) { this.numberOfQuestions = numberOfQuestions; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}