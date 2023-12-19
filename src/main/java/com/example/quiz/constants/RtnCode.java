package com.example.quiz.constants;

public enum RtnCode {
	SUCCESSFUL(200,"Successful!!"),//
	PARAM_ERROR(400,"Param error"),//
	ACCOUNT_NOT__FOUND(404,"Account not found!!"),//
	DATE_FORMAT_ERROR(400,"Date format error!!"),//
	ACCOUNT_EXISTED(400,"Account existed!!"),//
	QUESTION_IS_EMPTY(400,"Question is empty!!"),//
	QUESTION_PARAM_ERROR(400,"Question param error"),//
	QUIZ_CREATE_ERROR(400,"Quiz create error"),//
	QUIZ_NOT_FOUMD(400,"Quiz not foumd"),//
	QUIZ_CANNOT_BE_UPDATED(400,"Quiz cannot be updataed"),//
	QUIZ_ID_LIST_IS_EMPTY(400,"Quiz id list is empty"),//
	QUESTION_DELETE_ERROR(400,"Question delete reeor");
	
	

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
