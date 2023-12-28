package com.example.quiz.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizSearchReq {

	@JsonProperty(value = "quiz_name")
	private String quizName;
	
	@JsonProperty(value = "start_data")
	private LocalDate startDate;
	
	@JsonProperty(value = "end_data")
	private LocalDate endDate ;
	
	@JsonProperty(value = "is_longin")
	private boolean longin;

	public QuizSearchReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizSearchReq(String quizName, LocalDate startDate, LocalDate endDate, boolean longin) {
		super();
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.longin = longin;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isLongin() {
		return longin;
	}

	public void setLongin(boolean longin) {
		this.longin = longin;
	}

		
	
}
