package com.example.quiz.service.ifs;

import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.StatisticsRes;
import com.example.quiz.vo.WriterGetRes;
import com.example.quiz.vo.WriterReq;

public interface WriterService {

	public QuizRes write(WriterReq req);
	
	public WriterGetRes findByQuizNum(int quizNum);
	
	public StatisticsRes count(int quizNum);
}
