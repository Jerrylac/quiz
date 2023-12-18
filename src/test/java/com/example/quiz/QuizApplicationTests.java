package com.example.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginRes;

@SpringBootTest
class QuizApplicationTests {

	@Autowired
	public UserService userService;
	
	@Test
	public void UserConteTest() {
		 UserLoginRes res = userService.create("A01", "AA123");
		 System.out.println(res.getRencode().getMessage());
	}

}
