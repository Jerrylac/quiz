package com.example.quiz.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quiz")
public class Quiz {

	//加此注釋可讓資料 save 進 DB 後，可以得到 AI(Auto Increment) 欄位的值
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="num")
	private int num;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="questions")
	private String questionList;

	@Column(name="is_published")
	private boolean published;
	
	public Quiz() {
		super();
	}

	

	public Quiz(String name, String description, LocalDate startDate, LocalDate endDate, String questionList,
			boolean published) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.questionList = questionList;
	}



	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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



	public String getQuestionList() {
		return questionList;
	}



	public void setQuestionList(String questionList) {
		this.questionList = questionList;
	}



	public boolean isPublished() {
		return published;
	}



	public void setPublished(boolean published) {
		this.published = published;
	}
	
	
}
