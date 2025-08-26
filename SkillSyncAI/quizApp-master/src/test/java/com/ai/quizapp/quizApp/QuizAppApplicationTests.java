package com.ai.quizapp.quizApp;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(classes = QuizAppApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
@TestPropertySource(properties = {"gemini_api_key=AIzaSyB8PLx-3dgX2q8BfRCi5leblqmBjCFa0HI"})
public class QuizAppApplicationTests {
	@Test
	void contextLoads() {
		System.out.println("Context loaded: ");
	}
}

