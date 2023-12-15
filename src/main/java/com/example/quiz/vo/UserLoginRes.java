package com.example.quiz.vo;

import com.example.quiz.constants.RtnCode;

public class UserLoginRes {
	
	private RtnCode rencode;

	public UserLoginRes() {
		super();
	}

	public UserLoginRes(RtnCode rencode) {
		super();
		this.rencode = rencode;
	}

	public RtnCode getRencode() {
		return rencode;
	}

	public void setRencode(RtnCode rencode) {
		this.rencode = rencode;
	}
	
}
