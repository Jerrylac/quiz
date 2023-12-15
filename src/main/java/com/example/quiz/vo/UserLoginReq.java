package com.example.quiz.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginReq {
	
	private String accout;
	
	@JsonProperty("password")
	private String pwd;

	public String getAccout() {
		return accout;
	}

	public void setAccout(String accout) {
		this.accout = accout;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	
	
}
