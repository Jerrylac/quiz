package com.example.quiz.vo;

import java.util.Map;

public class Count {
	
	private int questionNum;
	//			�ﶵ 	����
	private Map<String, Integer> optionCountMap;
	
	public Count() {
		super();
	}

	public Count(int questionNum, Map<String, Integer> optionCountMap) {
		super();
		this.questionNum = questionNum;
		this.optionCountMap = optionCountMap;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public Map<String, Integer> getOptionCountMap() {
		return optionCountMap;
	}

	public void setOptionCountMap(Map<String, Integer> optionCountMap) {
		this.optionCountMap = optionCountMap;
	}
	
		
	
	
}
