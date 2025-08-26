package com.ai.quizapp.quizApp.repo;

import com.ai.quizapp.quizApp.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResultRepository extends JpaRepository<Result, String> {
    List<Result> findByUserInfoUserEmail(String userEmail);
}