package com.example.quiz.vo;

import java.util.List;

public class Statistics extends QuizRes{

	private int quizNum;
	
	private List<Count> countList;

	public Statistics() {
		super();
	}

	public Statistics(int quizNum, List<Count> countList) {
		super();
		this.quizNum = quizNum;
		this.countList = countList;
	}
	
	public Statistics(int code, String message,int quizNum, List<Count> countList) {
		super(code, message);
		this.quizNum = quizNum;
		this.countList = countList;
	}

	public int getQuizNum() {
		return quizNum;
	}

	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}

	public List<Count> getCountList() {
		return countList;
	}

	public void setCountList(List<Count> countList) {
		this.countList = countList;
	}
	
	
}
