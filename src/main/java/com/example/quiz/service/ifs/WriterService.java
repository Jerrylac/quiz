package com.example.quiz.service.ifs;

import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.WriterReq;

public interface WriterService {

	public QuizRes write(WriterReq req);
}
