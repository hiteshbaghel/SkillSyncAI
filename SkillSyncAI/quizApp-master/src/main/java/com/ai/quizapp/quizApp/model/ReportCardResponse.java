package com.ai.quizapp.quizApp.model;

import java.util.List;

public class ReportCardResponse {
    private int totalPoints;
    private List<QuizReport> quizReports;

    // Constructors
    public ReportCardResponse() {}

    public ReportCardResponse(int totalPoints, List<QuizReport> quizReports) {
        this.totalPoints = totalPoints;
        this.quizReports = quizReports;
    }

    // Getters and setters
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<QuizReport> getQuizReports() {
        return quizReports;
    }

    public void setQuizReports(List<QuizReport> quizReports) {
        this.quizReports = quizReports;
    }
}
