package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="question")
@IdClass(value = Question1Id.class)
public class Question1 {

	@Id
	@Column(name="quiz_num")
	public int quizNum;
	
	@Id
	@Column(name="num")
	public int num;
	
	@Column(name="title")
	public String title;
	
	@Column(name="type")
	public String type;
	
	@Column(name="is_necessary")
	public boolean necessary;
	
	@Column(name="options")
	public String options;
	
	@Column(name="is_published")
	public boolean published;

	public Question1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question1(String title, String type, boolean necessary, String options, boolean published) {
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
