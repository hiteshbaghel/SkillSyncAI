package com.ai.quizapp.quizApp.controller;

import com.ai.quizapp.quizApp.entity.Feedback;
import com.ai.quizapp.quizApp.model.FeedbackRequest;
import com.ai.quizapp.quizApp.model.FeedbackResponse;
import com.ai.quizapp.quizApp.model.SuccessResponse;
import com.ai.quizapp.quizApp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Endpoint to submit feedback     feedbackRequest.getUserName(),

    @PostMapping
    public ResponseEntity<SuccessResponse> submitFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        feedbackService.submitFeedback(
                feedbackRequest.getUserEmail(),
                feedbackRequest.getFeedback()
        );
        return new ResponseEntity<>(new SuccessResponse("Feedback submitted successfully!"), HttpStatus.CREATED);
    }

    // Endpoint to get feedback by user email
//    @GetMapping("/user/{userEmail}")
//    public ResponseEntity<List<Feedback>> getFeedbackByUser(@PathVariable String userEmail) {
//        List<Feedback> feedback = feedbackService.getFeedbackByUser(userEmail);
//        return ResponseEntity.ok(feedback);
//    }

    // Endpoint to get all feedback with specific fields
    @GetMapping("/hide403")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedback() {
        List<FeedbackResponse> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }
}