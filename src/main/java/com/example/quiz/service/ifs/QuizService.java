package com.example.quiz.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz.entity.Question;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizRes;

public interface QuizService {

	public QuizRes create(String name,String description,LocalDate startData,LocalDate endDate,List<Question>questionList,
			boolean published);
	
	public QuizRes upDate(int num ,String name,String description,LocalDate startData,LocalDate endDate
			,List<Question>questionList,boolean published);
	
	public QuizRes deleteQuiz(List<Integer> numlList);
	
	public QuizRes deleteQuestion(int quizNum,List<Integer> numlList);
	
	public QuizGetRes search(String quizName,LocalDate startDate,LocalDate endDate ,boolean isLongin);
}
