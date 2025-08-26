package com.ai.quizapp.quizApp.repo;

import com.ai.quizapp.quizApp.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findByUser_UserEmail(String userEmail); // Corrected to navigate the 'user' relationship
}