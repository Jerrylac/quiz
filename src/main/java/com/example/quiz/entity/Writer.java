package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="writer")
public class Writer {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="num")
	public int num;
	
	@Column(name="quiz_num")
	public int quizNum;
	
	@Column(name="name")
	public String name;
	
	@Column(name="phone")
	public String phone;
	
	@Column(name="email")
	public String email;
	
	@Column(name="age")
	public int age;

	public Writer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Writer(int quizNum, String name, String phone, String email, int age) {
		super();
		this.quizNum = quizNum;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getQuizNum() {
		return quizNum;
	}

	public void setQuizNum(int quizNum) {
		this.quizNum = quizNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
