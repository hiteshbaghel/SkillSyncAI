package com.ai.quizapp.quizApp.controller;
import com.ai.quizapp.quizApp.model.ReportCardResponse;
import com.ai.quizapp.quizApp.entity.Result;
import com.ai.quizapp.quizApp.entity.UserInfo;
import com.ai.quizapp.quizApp.model.*;
import com.ai.quizapp.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"https://skillsyncai.vercel.app", "http://localhost:8080"})
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        System.out.println("Request: " + request.getEmail() + " " + request.getProfileUrl() + " " + request.getName());
        return quizService.loginUser(request.getEmail(), request.getProfileUrl(), request.getName());
    }

    @GetMapping("/leaderboard")
    public List<UserInfo> getLeaderboard() {
        return quizService.getLeaderboard();
    }

    @PostMapping("/report-card")
    public ReportCardResponse getReportCard(@RequestBody ReportCardRequest request) { // Updated return type
        return quizService.getReportCard(request.getEmail());
    }

    @PostMapping("/generate-test")
    public Map<String, Object> generateTest(@RequestBody TestRequest request) {
        return quizService.generateTest(request.getSubjectName(), request.getDifficulty(), request.getNumberOfQuestions());
    }

    @PostMapping("/result")
    public Map<String, Object> submitResult(@RequestBody ResultRequest request) {
        return quizService.submitResult(
                request.getUserEmail(),
                request.getQuizID(),
                request.getTimeTaken(),
                request.getUserAnswers()
        );
    }
}