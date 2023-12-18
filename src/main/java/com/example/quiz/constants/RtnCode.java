package com.example.quiz.constants;

public enum RtnCode {
	SUCCESSFUL(200,"Successful!!"),//
	PARAM_ERROR(400,"Param error"),//
	ACCOUNT_NOT__FOUND(404,"Account not found!!"),//
	DATE_FORMAT_ERROR(400,"Date format error!!"),//
	ACCOUNT_EXISTED(400,"Account existed!!");
	
	

	private int code;
	
	private String message;

	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
