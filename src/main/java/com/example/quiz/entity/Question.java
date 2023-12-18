package com.example.quiz.entity;

public class Question {


	public int quizNum;
	
	public int num;
	
	public String title;
	
	public String type;
	
	public boolean necessary;
	
	public String options;
	
	public boolean published;


	public Question() {
		super();
	}

	public Question(String title, String type, boolean necessary, String options, boolean published) {
		super();
		this.title = title;
		this.type = type;
		this.necessary = necessary;
		this.options = options;
		this.published = published;
	}

	public int getQuizNum() {
		return quizNum;
	}

	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
}
