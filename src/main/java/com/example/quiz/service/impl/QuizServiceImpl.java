package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.QuizRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {
//	字串與物件(類別)互轉
	private ObjectMapper mapper=new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Override
	public QuizRes create(String name, String description, LocalDate startData, LocalDate endDate,
			List<Question>questionList,boolean published) {

		if (!StringUtils.hasText(name) || !StringUtils.hasText(description) || startData == null || endDate == null) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		//允許先建立問卷資料，而不同時建立問題
		if(!CollectionUtils.isEmpty(questionList)) {
			//check question
			QuizRes checkResult=checkQuestion(questionList);
			if(checkResult != null) {
				return checkResult;
			}	
		}
		if (startData.isAfter(endDate)) {
			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(), RtnCode.DATE_FORMAT_ERROR.getMessage());
		}
		try {
			String str = mapper.writeValueAsString(questionList);
			quizDao.save(new Quiz(name, description, startData, endDate,str, published));
			return new QuizRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return new QuizRes(RtnCode.QUIZ_CREATE_ERROR.getCode(), RtnCode.QUIZ_CREATE_ERROR.getMessage());
		}
			 
		
	}
	private QuizRes checkQuestion(List<Question>questionList) {
		for(Question item:questionList) {
			if(item.getNum()==0||!StringUtils.hasText(item.getTitle())
					||!StringUtils.hasText(item.getType()) ) {
				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR.getCode(), RtnCode.QUESTION_PARAM_ERROR.getMessage());
			}
		}
		return null;
	}
	@Override
	public QuizRes upDate(int num, String name, String description, LocalDate startData, LocalDate endDate,
			List<Question> questionList, boolean published) {
		
		return null;
	}

}
