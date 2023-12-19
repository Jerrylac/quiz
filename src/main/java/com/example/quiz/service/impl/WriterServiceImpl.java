package com.example.quiz.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.WriterService;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.WriterReq;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WriterServiceImpl implements WriterService{

	private ObjectMapper mapper=new ObjectMapper();
	
	@Autowired
	private QuizDao quizDao;
	
	@Override
	public QuizRes write(WriterReq req) {
		if(req.getQuizNum()<=0||!StringUtils.hasText(req.getName())) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		Optional<Quiz> op = quizDao.findById(req.quizNum);
		if(op.isEmpty()) {
			return new QuizRes(RtnCode.QUIZ_NOT_FOUMD.getCode(), RtnCode.QUIZ_NOT_FOUMD.getMessage());
		}
		Quiz quiz=op.get();
		
		return null;
	}

}
