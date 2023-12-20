package com.example.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Writer;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.repository.WriterDao;
import com.example.quiz.service.ifs.WriterService;
import com.example.quiz.vo.Count;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.Statistics;
import com.example.quiz.vo.WriterGetRes;
import com.example.quiz.vo.WriterReq;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WriterServiceImpl implements WriterService{

	private ObjectMapper mapper=new ObjectMapper();
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private WriterDao writerDao;
	
	@Override
	public QuizRes write(WriterReq req) {
		if(req.getQuizNum()<=0||!StringUtils.hasText(req.getName())) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getAnswer())) {
			return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(), RtnCode.NO_QUESTION_ANSWER.getMessage());
		}
		//把answer的字串轉成map
		try {
			Map<String,String> map = mapper.readValue(req.getAnswer(), Map.class);
			Optional<Quiz> op = quizDao.findById(req.quizNum);
			if(op.isEmpty()) {
				return new QuizRes(RtnCode.QUIZ_NOT_FOUMD.getCode(), RtnCode.QUIZ_NOT_FOUMD.getMessage());
			}
			Quiz quiz=op.get();
			String questionStr=quiz.getQuestionStr();
			List<Question> questionList=mapper.readValue(questionStr,
					new TypeReference<List<Question>>() {});
			//檢查必填問題是否有答案
			for (Question qu : questionList) {
				for ( Entry<String, String> item : map.entrySet()) {
					if(String.valueOf(qu.getNum()).equals(item.getKey())&&
							qu.isNecessary()&&!StringUtils.hasText(item.getValue())) {
						return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(), RtnCode.NO_QUESTION_ANSWER.getMessage());
					}
				}
			}
		} catch (Exception e) {
			return new QuizRes(RtnCode.STRING_PARSER_ERROR.getCode(), RtnCode.STRING_PARSER_ERROR.getMessage());
		}
		
		writerDao.save(new Writer(req));
		return new QuizRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public WriterGetRes findByQuizNum(int quizNum) {
		if(quizNum<=0) {
			return new WriterGetRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage(),null);
		}
		List<Writer> res = writerDao.findByQuizNumOrderByNumDesc(quizNum);
		return new WriterGetRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),res);
	}

	@Override
	public Statistics count(int quizNum) {
		if(quizNum<=0) {
			return new Statistics(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage()
					,quizNum,null);
		}
		List<Writer> res = writerDao.findByQuizNum(quizNum);
		List<Count> countsLis= new ArrayList<>();
		for (Writer item : res) {
			String answerStr=item.getAnswer();
			try {
				//	問題編號,選項  -->選項有多個時,用逗號串接
				Map<String,String> answerMap = mapper.readValue(answerStr, Map.class);
				for ( Entry<String, String> mapItem : answerMap.entrySet()) {
					String valueStr=mapItem.getValue();
					valueStr = StringUtils.trimAllWhitespace(valueStr);
					String[] array=valueStr.split(",");
					Map<String, Integer> optionCountMap=new HashMap<>();
					for (String arrayItem : array) {
						
						optionCountMap.put(arrayItem, null);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return new Statistics(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage()
				,quizNum,countsLis);
	}

}
