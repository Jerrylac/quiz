package com.example.quiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteQuizReq {

	@JsonProperty("quiz_num_list")
	private List<Integer> numlList;

	public List<Integer> getNumlList() {
		return numlList;
	}

	public void setNumlList(List<Integer> numlList) {
		this.numlList = numlList;
	}
	
	
}
