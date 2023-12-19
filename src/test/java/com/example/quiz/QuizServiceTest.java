package com.example.quiz;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.entity.Question;
import com.example.quiz.service.ifs.QuizService;

@SpringBootTest
public class QuizServiceTest {
	
	@Autowired
	private QuizService quizService;
	
	@Test
	public void creatQuizTest() {
		List<Question> list=Arrays.asList(
				new Question(1,"test1","single",true,"AAA;BBB,CCC"),
				new Question(2,"test2","single",false,"QQQ;WWW,EEE"));
		quizService.create("AAA", "BBB",LocalDate.now(), LocalDate.now().plusDays(1), list, false);
	}
}
