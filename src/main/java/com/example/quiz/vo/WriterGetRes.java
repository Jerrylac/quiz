package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Writer;

public class WriterGetRes extends QuizRes {

	private List<Writer> writerList;

	public WriterGetRes() {
		super();
	}

	public WriterGetRes(int code, String message, List<Writer> writerList) {
		super(code, message);
		this.writerList = writerList;
	}

	public List<Writer> getWriterList() {
		return writerList;
	}

	public void setWriterList(List<Writer> writerList) {
		this.writerList = writerList;
	}

}
