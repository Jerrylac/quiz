package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class QuizGetRes extends QuizRes{

	private List<Quiz> quizList;

	public QuizGetRes() {
		super();
	}

	public QuizGetRes(int code, String message,List<Quiz> quizList) {
		super(code, message);
		this.quizList = quizList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

	public void setQuizList(List<Quiz> quizList) {
		this.quizList = quizList;
	}
	
}
