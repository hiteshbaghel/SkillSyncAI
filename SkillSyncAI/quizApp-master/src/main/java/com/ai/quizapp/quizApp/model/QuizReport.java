package com.ai.quizapp.quizApp.model;

public class QuizReport {
    private String quizId;
    private String resultId;
    private int quizScore;
    private double timeTaken;
    private int totalNoOfQuestions;
    private int totalCorrectAnswers;
    private String subjectName;
    private String dateTime;
    private String difficultyLevel;

    // Constructors
    public QuizReport() {}

    public QuizReport(String quizId, String resultId, int quizScore, double timeTaken, int totalNoOfQuestions,
                      int totalCorrectAnswers, String subjectName, String dateTime, String difficultyLevel) {
        this.quizId = quizId;
        this.resultId = resultId;
        this.quizScore = quizScore;
        this.timeTaken = timeTaken;
        this.totalNoOfQuestions = totalNoOfQuestions;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.subjectName = subjectName;
        this.dateTime = dateTime;
        this.difficultyLevel = difficultyLevel;
    }

    // Getters and setters
    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }

    public String getResultId() { return resultId; }
    public void setResultId(String resultId) { this.resultId = resultId; }

    public int getQuizScore() { return quizScore; }
    public void setQuizScore(int quizScore) { this.quizScore = quizScore; }

    public double getTimeTaken() { return timeTaken; }
    public void setTimeTaken(double timeTaken) { this.timeTaken = timeTaken; }

    public int getTotalNoOfQuestions() { return totalNoOfQuestions; }
    public void setTotalNoOfQuestions(int totalNoOfQuestions) { this.totalNoOfQuestions = totalNoOfQuestions; }

    public int getTotalCorrectAnswers() { return totalCorrectAnswers; }
    public void setTotalCorrectAnswers(int totalCorrectAnswers) { this.totalCorrectAnswers = totalCorrectAnswers; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
}
