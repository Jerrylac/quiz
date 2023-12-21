package com.example.quiz.vo;

import java.util.List;

public class Answer {

	private int qNum;
	
	private List<String> optionList;

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Answer(int qNum, List<String> optionList) {
		super();
		this.qNum = qNum;
		this.optionList = optionList;
	}

	public int getqNum() {
		return qNum;
	}

	public void setqNum(int qNum) {
		this.qNum = qNum;
	}

	public List<String> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}
	
	
	
}
