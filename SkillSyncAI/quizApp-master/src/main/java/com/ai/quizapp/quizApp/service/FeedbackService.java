package com.ai.quizapp.quizApp.service;

import com.ai.quizapp.quizApp.entity.Feedback;
import com.ai.quizapp.quizApp.entity.UserInfo;
import com.ai.quizapp.quizApp.model.FeedbackResponse;
import com.ai.quizapp.quizApp.repo.FeedbackRepository;
import com.ai.quizapp.quizApp.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;
// String userName,
    public void submitFeedback(String userEmail,  String feedbackText) {
        // Check if user exists; throw an exception if not found
        UserInfo user = userInfoRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email " + userEmail + " not found in the database"));

        // Create and save feedback
        Feedback feedback = new Feedback(user, feedbackText);    //, userName
        feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByUser(String userEmail) {
        return feedbackRepository.findByUser_UserEmail(userEmail); // Updated to match repository method
    }

    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAll().stream()
                .map(feedback -> new FeedbackResponse(
                        feedback.getFid(),
                        feedback.getUser() != null ? feedback.getUser().getUserEmail() : "Unknown",
//                        feedback.getUserName(),
                        feedback.getCreatedAt(),
                        feedback.getFeedback()
                ))
                .collect(Collectors.toList());
    }
}