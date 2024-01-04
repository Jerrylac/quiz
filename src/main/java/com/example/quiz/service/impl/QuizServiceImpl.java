package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {
	
	//slf4j
	private Logger logger=LoggerFactory.getLogger(getClass());
	
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
//		if(!CollectionUtils.isEmpty(questionList)) {
//			//check question
//			QuizRes checkResult=checkQuestion(questionList);
//			if(checkResult != null) {
//				return checkResult;
//			}	
//		}
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
//	private QuizRes checkQuestion(List<Question>questionList) {
//		for(Question item:questionList) {
//			if(item.getNum()==0||!StringUtils.hasText(item.getTitle())
//					||!StringUtils.hasText(item.getType()) ) {
//				return new QuizRes(RtnCode.QUESTION_PARAM_ERROR.getCode(), RtnCode.QUESTION_PARAM_ERROR.getMessage());
//			}
//		}
//		return null;
//	}
	@Override
	public QuizRes upDate(int num, String name, String description, LocalDate startData, LocalDate endDate,
			List<Question> questionList, boolean published) {
		if(num<=0) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
//		if(!CollectionUtils.isEmpty(questionList)) {
//			//check question
//			QuizRes checkResult=checkQuestion(questionList);
//			if(checkResult != null) {
//				return checkResult;
//			}	
//		}
		Optional<Quiz> op= quizDao.findById(num);
		if(op.isEmpty()) {
			return new QuizRes(RtnCode.QUIZ_NOT_FOUMD.getCode(), RtnCode.QUIZ_NOT_FOUMD.getMessage());
		}
		Quiz quiz=op.get();
		//判斷已發布且已開始(當前時間大於開始時間)不能更新
		if(quiz.isPublished()&&LocalDate.now().isAfter(quiz.getEndDate())) {
			return new QuizRes(RtnCode.QUIZ_CANNOT_BE_UPDATED.getCode(), RtnCode.QUIZ_CANNOT_BE_UPDATED.getMessage());
		}
		if(StringUtils.hasText(name)) {
			quiz.setName(name);
		}
		if(StringUtils.hasText(description)) {
			quiz.setDescription(description);
		}
		if (startData.isAfter(endDate)) {
			return new QuizRes(RtnCode.DATE_FORMAT_ERROR.getCode(), RtnCode.DATE_FORMAT_ERROR.getMessage());
		}
		if(startData!= null) {
			quiz.setStartDate(startData);
		}
		if(endDate!= null) {
			quiz.setEndDate(endDate);
		}
		quiz.setPublished(published);
		try {
			String str = mapper.writeValueAsString(questionList);
			quiz.setQuestionStr(str);
			quizDao.save(new Quiz(num ,name, description, startData, endDate,str, published));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return new QuizRes(RtnCode.QUIZ_CREATE_ERROR.getCode(), RtnCode.QUIZ_CREATE_ERROR.getMessage());
		}
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
	}
	@Override
	public QuizRes deleteQuiz(List<Integer> numlList) {
		if(CollectionUtils.isEmpty(numlList)) {
			return new QuizRes(RtnCode.QUIZ_ID_LIST_IS_EMPTY.getCode(), RtnCode.QUIZ_ID_LIST_IS_EMPTY.getMessage());
		}
		List<Quiz> quizList = quizDao.findAllById(numlList);
		for (Quiz item : quizList) {
			//判斷已發布且已開始(當前時間大於開始時間)不能更新
			if(item.isPublished()&&LocalDate.now().isAfter(item.getEndDate())) {
				return new QuizRes(RtnCode.QUIZ_CANNOT_BE_UPDATED.getCode(), RtnCode.QUIZ_CANNOT_BE_UPDATED.getMessage());
			}
		}
		try {
			quizDao.deleteAllById(numlList);	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
	}
	@Override
	public QuizRes deleteQuestion(int quizNum, List<Integer> numList) {
		if(quizNum<=0||CollectionUtils.isEmpty(numList)) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		Optional<Quiz> op = quizDao.findById(quizNum);
		if(op.isEmpty()) {
			return new QuizRes(RtnCode.QUIZ_NOT_FOUMD.getCode(), RtnCode.QUIZ_NOT_FOUMD.getMessage());
		}
		//quiz等於把op取出Optional的Quiz
		Quiz quiz = op.get();
		String questionStr=quiz.getQuestionStr();
		try {
			List<Question> questionList=mapper.readValue(questionStr,
					new TypeReference<List<Question>>() {});
			List<Question> newList=new ArrayList<>();
			int index=1;
			for (Question item : questionList) {
				//numList是要被刪除的清單 newList是要保留的清單 所以要把newList 取代numList清單
				if(!numList.contains(item.getNum())) {
					item.setNum(index);
					newList.add(item);
					index++;
				}
			}
			String retainListStr=mapper.writeValueAsString(newList);
			quiz.setQuestionStr(retainListStr);
			quizDao.save(quiz);
		} catch (Exception e) {
			return new QuizRes(RtnCode.QUESTION_DELETE_ERROR.getCode(), RtnCode.QUESTION_DELETE_ERROR.getMessage());
		}
		
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
	}
	//當search都為null的話代表搜尋所有資料
	@Override
	public QuizGetRes search(String quizName, LocalDate startDate, LocalDate endDate,boolean isLongin) {
		quizName=!StringUtils.hasText(quizName)?"":quizName;
		startDate=startDate==null?LocalDate.of(1970, 01, 01):startDate;
		endDate=endDate==null?LocalDate.of(2099, 12, 31):endDate;
		List<Quiz> res=isLongin?quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName, startDate, endDate)
				:quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(quizName, startDate, endDate);
		return new QuizGetRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),res);
	}

}
