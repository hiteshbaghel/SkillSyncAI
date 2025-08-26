package com.ai.quizapp.quizApp.repo;

import com.ai.quizapp.quizApp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByUserEmail(String userEmail);
}