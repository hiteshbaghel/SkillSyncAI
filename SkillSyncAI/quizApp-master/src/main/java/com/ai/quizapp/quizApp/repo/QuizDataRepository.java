package com.ai.quizapp.quizApp.repo;

import com.ai.quizapp.quizApp.entity.QuizData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDataRepository extends JpaRepository<QuizData, String> {
}
