package com.example.quiz.vo;

import java.util.List;

public class StatisticsRes extends QuizRes{

	private int quizNum;
	
	private List<Count> countList;

	public StatisticsRes() {
		super();
	}

	public StatisticsRes(int quizNum, List<Count> countList) {
		super();
		this.quizNum = quizNum;
		this.countList = countList;
	}
	
	public StatisticsRes(int code, String message,int quizNum, List<Count> countList) {
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
