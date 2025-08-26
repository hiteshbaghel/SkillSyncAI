package com.ai.quizapp.quizApp.service;

import com.ai.quizapp.quizApp.model.QuizReport;
import com.ai.quizapp.quizApp.model.ReportCardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ai.quizapp.quizApp.entity.QuizData;
import com.ai.quizapp.quizApp.entity.Result;
import com.ai.quizapp.quizApp.entity.UserInfo;
import com.ai.quizapp.quizApp.repo.QuizDataRepository;
import com.ai.quizapp.quizApp.repo.ResultRepository;
import com.ai.quizapp.quizApp.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QuizService {

    @Value("${gemini_api_key}")
    private String geminiApiKey;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private QuizDataRepository quizDataRepository;

    private static final String GEMINI_API_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Change to store correct answers per quizID
    private final Map<String, Map<String, String>> quizCorrectAnswers = new ConcurrentHashMap<>();

    public QuizService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GEMINI_API_ENDPOINT).build();
    }

    public Map<String, Object> loginUser(String email, String profileUrl, String name) { // Changed to Map<String, Object>
        Optional<UserInfo> existingUser = userInfoRepository.findById(email);
        if (existingUser.isPresent()) {
            UserInfo user = existingUser.get();
            if (user.getUserName().equals(name) && user.getProfileUrl().equals(profileUrl)) {
                return Map.of(
                        "message", "Login successfully",
                        "userPoints", user.getUserPoints()
                );
            } else {
                user.setUserName(name);
                user.setProfileUrl(profileUrl);
                userInfoRepository.save(user);
                return Map.of(
                        "message", "Login successfully, user data updated",
                        "userPoints", user.getUserPoints()
                );
            }
        } else {
            UserInfo newUser = new UserInfo();
            newUser.setUserEmail(email);
            newUser.setUserName(name);
            newUser.setProfileUrl(profileUrl);
            newUser.setUserPoints(0);
            userInfoRepository.save(newUser);
            return Map.of(
                    "message", "Login successfully, new user created",
                    "userPoints", 0
            );
        }
    }
    public List<UserInfo> getLeaderboard() {
        List<UserInfo> users = userInfoRepository.findAll();
        users.sort((u1, u2) -> Integer.compare(u2.getUserPoints(), u1.getUserPoints()));
        return users;
    }

    public ReportCardResponse getReportCard(String email) {
        UserInfo user = userInfoRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        int totalPoints = user.getUserPoints();

        // Fetch all results for the user
        List<Result> results = resultRepository.findByUserInfoUserEmail(email);
        List<QuizReport> quizReports = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Result result : results) {
            QuizData quizData = result.getQuizData();
            QuizReport quizReport = new QuizReport(
                    quizData.getQuizID(),
                    result.getResultId(),
                    result.getQuizScore(),
                    result.getTimeTaken(),
                    quizData.getNumberOfQuestions(),
                    result.getTotalCorrectAnswers(),
                    quizData.getSubjectName(),
                    quizData.getDateTime().format(formatter),
                    quizData.getDifficultyLevel()
            );
            quizReports.add(quizReport);
        }

        return new ReportCardResponse(totalPoints, quizReports);
    }

    public Map<String, Object> generateTest(String subjectName, String difficulty, int numberOfQuestions) {

        String randomHint = "Include a random historical or cultural reference for variety.";
        String prompt = String.format("Generate %d unique and random quiz questions on the subject '%s' with difficulty level '%s'. " +
                        "Ensure each question is distinct and varied, avoiding repetition from previous responses. " +
                        randomHint +
                        "Return questions in JSON format: 'questions': [{'questionID': int, 'Questions': 'text', 'Options': ['Option1', 'Option2', 'Option3', 'Option4'], 'correctAnswer': 'Correct Option'}]",
                numberOfQuestions, subjectName, difficulty);

//        String prompt = String.format("Generate %d quiz questions on the subject '%s' with difficulty level '%s'. " +
//                        "Return questions in JSON format: 'questions': [{'questionID': int, 'Questions': 'text', 'Options': ['Option1', 'Option2', 'Option3', 'Option4'], 'correctAnswer': 'Correct Option'}]",
//                numberOfQuestions, subjectName, difficulty);

        List<Map<String, Object>> questions = callGeminiAPI(prompt);

        // Generate attractive quizID (stored in DB)
        String quizID = "QZ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Store correct answers per quizID
        Map<String, String> correctAnswers = new HashMap<>();
        for (Map<String, Object> question : questions) {
            String questionId = String.valueOf(question.get("questionID"));
            String correctAnswer = (String) question.get("correctAnswer");
            correctAnswers.put(questionId, correctAnswer);
            question.remove("correctAnswer");
        }
        quizCorrectAnswers.put(quizID, correctAnswers);

        // Save quiz data with quizID
        QuizData quizData = new QuizData();
        quizData.setQuizID(quizID);
        quizData.setSubjectName(subjectName);
        quizData.setDifficultyLevel(difficulty);
        quizData.setNumberOfQuestions(numberOfQuestions);
        quizData.setDateTime(LocalDateTime.now());
        quizDataRepository.save(quizData);
        System.out.println(questions);
        // Return only quizId and questions to frontend
        return Map.of(
                "quizID", quizID,
                "questions", questions
        );
    }
    private int getPointsMultiplierForDifficulty(String difficultyLevel) {
        switch (difficultyLevel.toLowerCase()) {
            case "easy":
                return 1;
            case "medium":
                return 2;
            case "hard":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + difficultyLevel);
        }
    }
    public Map<String, Object> submitResult(String userEmail, String quizID, double timeTaken, Map<String, String> userAnswers) {
        int totalQuestions = userAnswers.size();
        int totalCorrect = 0;

        // Retrieve correct answers for this specific quizID
        Map<String, String> correctAnswers = quizCorrectAnswers.get(quizID);
        if (correctAnswers == null) {
            throw new RuntimeException("No correct answers found for quizID: " + quizID);
        }

        // Collect only questionId and correctAnswer for each question (key-value pair)
        Map<String, String> questionCorrectAnswers = new HashMap<>();
        for (String questionId : userAnswers.keySet()) {
            String correctAnswer = correctAnswers.get(questionId);
            if (correctAnswer != null) {
                questionCorrectAnswers.put(questionId, correctAnswer);
            }
        }

        // Retrieve the quiz to get the difficulty level
        QuizData quizData = quizDataRepository.findById(quizID)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));
        String difficultyLevel = quizData.getDifficultyLevel();

        // Determine points multiplier based on difficulty level
        int pointsMultiplier = getPointsMultiplierForDifficulty(difficultyLevel);

        // Evaluate user answers to calculate totalCorrect
        for (Map.Entry<String, String> entry : userAnswers.entrySet()) {
            String questionId = entry.getKey();
            String userAnswer = entry.getValue();
            String correctAnswer = correctAnswers.get(questionId);
            if (correctAnswer != null && correctAnswer.equals(userAnswer)) {
                totalCorrect++;
            }
        }

        // Calculate total userPoints based on difficulty (multiply totalCorrect by pointsMultiplier)
        int userPoints = totalCorrect * pointsMultiplier;

        // Update user points in UserInfo (store in user_info table)
        UserInfo user = userInfoRepository.findById(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUserPoints(user.getUserPoints() + userPoints);
        userInfoRepository.save(user);

        // Use the existing quizData for Result (no userPoints in Result)
        String resultId = "RS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Result result = new Result();
        result.setResultId(resultId);
        result.setUserInfo(user);
        result.setQuizData(quizData);
        result.setTotalCorrectAnswers(totalCorrect);
        result.setQuizScore((totalCorrect * 100) / totalQuestions); // Percentage score remains the same
        result.setTimeTaken(timeTaken);
        resultRepository.save(result);

        // Optionally, remove correct answers after submission to save memory (if not needed for re-submission)
        quizCorrectAnswers.remove(quizID);

        // Return summary data, questionId-correctAnswer pairs, userPoints, and pointsMultiplier
        return Map.of(
                "resultId", resultId,
                "quizID", quizID,
                "timeTaken", timeTaken,
                "totalCorrectAnswers", totalCorrect,
                "userPoints", userPoints,// Include total points earned in response
              //  "pointsMultiplier", pointsMultiplier, // Show scoring rule (multiplier)
                "quizScore", (totalCorrect * 100) / totalQuestions, // Percentage score
                "noOfQuestions", totalQuestions,
                "questionCorrectAnswers", questionCorrectAnswers
        );
    }
    private List<Map<String, Object>> callGeminiAPI(String prompt) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "contents", new Object[] {
                            Map.of("parts", new Object[] {
                                    Map.of("text", prompt)
                            })
                    }
            );

            String responseText = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", geminiApiKey).build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Map<String, Object> responseMap = objectMapper.readValue(responseText, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseMap.get("candidates");

            String generatedText;
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> candidate = candidates.get(0);
                Map<String, Object> contentResponse = (Map<String, Object>) candidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) contentResponse.get("parts");
                generatedText = (String) parts.get(0).get("text");
            } else {
                generatedText = responseText;
            }

            generatedText = generatedText.trim()
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            Map<String, Object> parsedResponse = objectMapper.readValue(generatedText, Map.class);
            List<Map<String, Object>> rawQuestions = (List<Map<String, Object>>) parsedResponse.get("questions");

            List<Map<String, Object>> questions = new ArrayList<>();
            for (Map<String, Object> question : rawQuestions) {
                Map<String, Object> formattedQuestion = new HashMap<>();
                formattedQuestion.put("questionID", question.get("questionID"));
                formattedQuestion.put("Questions", question.get("question"));
                formattedQuestion.put("Options", question.get("options"));
                formattedQuestion.put("correctAnswer", question.get("correctAnswer"));
                questions.add(formattedQuestion);
            }
            return questions;

        } catch (IOException e) {
            throw new RuntimeException("Error calling Gemini API: " + e.getMessage(), e);
        }
    }
}
