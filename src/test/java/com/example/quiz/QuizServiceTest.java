package com.example.quiz;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootTest
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
	
	@Test
	public void objecMapperTest1() {
		ObjectMapper mapper=new ObjectMapper();
		String str="[{\"num\":1,\"title\":\"test1\",\"type\":\"single\",\"necessary\":true,\"options\":\"AAA;BBB,CCC\"},"
				+ "{\"num\":2,\"title\":\"test2\",\"type\":\"single\",\"necessary\":false,\"options\":\"QQQ;WWW,EEE\"}]";
		try {
			List<Map<String, Object>> list = mapper.readValue(str, List.class);
			for (Map<String, Object> map : list) {
				for ( Entry<String, Object> item : map.entrySet()) {
					System.out.println("key: "+item.getKey());
					System.out.println("value: "+item.getValue());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void objecMapperTest2() {
		ObjectMapper mapper=new ObjectMapper();
		String str="{\"num\":1,\"title\":\"test1\",\"type\":\"single\",\"necessary\":true,\"options\":\"AAA;BBB,CCC\"}";
		try {
			Map<String, Object> res = mapper.readValue(str, Map.class);
				for ( Entry<String, Object> item : res.entrySet()) {
					System.out.println("key: "+item.getKey());
					System.out.println("value: "+item.getValue());
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
