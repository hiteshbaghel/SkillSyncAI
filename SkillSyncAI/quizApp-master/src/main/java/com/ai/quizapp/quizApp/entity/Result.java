package com.ai.quizapp.quizApp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESULT")
public class Result {
    @Id
    private String resultId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email", referencedColumnName = "userEmail") // Foreign key for UserInfo, referencing userEmail
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quizID", referencedColumnName = "quizID") // Foreign key for QuizData, referencing quizID
    private QuizData quizData;

    private int totalCorrectAnswers;
    private int quizScore;
    private double timeTaken;

    // Getters, setters, constructors
    public Result() {}

    public String getResultId() { return resultId; }
    public void setResultId(String resultId) { this.resultId = resultId; }

    public UserInfo getUserInfo() { return userInfo; }
    public void setUserInfo(UserInfo userInfo) { this.userInfo = userInfo; }

    public QuizData getQuizData() { return quizData; }
    public void setQuizData(QuizData quizData) { this.quizData = quizData; }

    public int getTotalCorrectAnswers() { return totalCorrectAnswers; }
    public void setTotalCorrectAnswers(int totalCorrectAnswers) { this.totalCorrectAnswers = totalCorrectAnswers; }

    public int getQuizScore() { return quizScore; }
    public void setQuizScore(int quizScore) { this.quizScore = quizScore; }

    public double getTimeTaken() { return timeTaken; }
    public void setTimeTaken(double timeTaken) { this.timeTaken = timeTaken; }
}