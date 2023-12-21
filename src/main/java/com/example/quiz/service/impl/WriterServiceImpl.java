package com.example.quiz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Question;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.Writer;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.repository.WriterDao;
import com.example.quiz.service.ifs.WriterService;
import com.example.quiz.vo.Answer;
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
			//[{"qNum":1,"optionList":["BBB"]},{"qNum":2,"optionList":["CCC"]},{"qNum":3,"optionList":["DDD"]}]
			List<Answer> ansList= mapper.readValue(req.getAnswer(), new TypeReference<List<Answer>>() {});
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
				for ( Answer item : ansList) {
					if(qu.getNum()==item.getqNum()&&
							qu.isNecessary()&&CollectionUtils.isEmpty(item.getOptionList())) {
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
		//產生問題編號與選項 List 的Map
		Map<Integer, List<String>> questionOptionMap=new HashMap<>();
		for (Writer item : res) {
			//[{"qNum":1,"optionList":["BBB"]},{"qNum":2,"optionList":["CCC"]},{"qNum":3,"optionList":["DDD"]}]
			String answerStr=item.getAnswer();
			try {
				List<Answer> ansList= mapper.readValue(answerStr, new TypeReference<>() {});
				for (Answer ans : ansList) {
					if(questionOptionMap.containsKey(ans.getqNum())) {
						//把 key 對應的 value 取出
						List<String> listInMap = questionOptionMap.get(ans.getqNum());
						//將 ans 中的 OptionList 增加到原本已存在 map 中的 option list
						listInMap.addAll(ans.getOptionList());
						questionOptionMap.put(ans.getqNum(), listInMap);
					}else {
						questionOptionMap.put(ans.getqNum(), ans.getOptionList());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//計算每個問題中每個選項次數
		List<Count> countList= new ArrayList<>();
		//		   <問題編號, 選項的List>								  是map裡抓key value
		for (Entry<Integer, List<String>> mapItem : questionOptionMap.entrySet()) {
			Count count=new Count();
			//計算每題每個選項的次數
			//map<選項問題 選擇次數>
			Map<String, Integer> optionCountMap=new HashMap<>();
			for (String str : mapItem.getValue()) {
				
				if(optionCountMap.containsKey(str)) {
					int oldCount =optionCountMap.get(str);
					oldCount++;
					optionCountMap.put(str, oldCount);
				}else {
					optionCountMap.put(str, 1);
				}
			}
			count.setQuestionNum(mapItem.getKey());
			count.setOptionCountMap(optionCountMap);
			countList.add(count);
		}
		return new Statistics(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage()
				,quizNum,countList);
	}
	
	
	//原本的write
	public QuizRes write1(WriterReq req) {
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
	
	//原本的count
	public Statistics count1(int quizNum) {
		if(quizNum<=0) {
			return new Statistics(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage()
					,quizNum,null);
		}
		List<Writer> res = writerDao.findByQuizNum(quizNum);
		//產生問題編號與選項 List 的Map
		Map<String, List<String>> questionOptionMap=new HashMap<>();
		for (Writer item : res) {
			String answerStr=item.getAnswer();
			try {
				//	問題編號,選項  -->選項有多個時,用逗號串接
				Map<String,String> answerMap = mapper.readValue(answerStr, Map.class);
				for ( Entry<String, String> mapItem : answerMap.entrySet()) {
					String valueStr=mapItem.getValue();
					valueStr = StringUtils.trimAllWhitespace(valueStr);
					String[] array=valueStr.split(",");
					List<String> optionList=new ArrayList<>();
					optionList.addAll(Arrays.asList(array));
					//將先前的所有回答(選項)從 questionOptionMap 取出
					List<String> listInMap=questionOptionMap.get(mapItem.getKey());
					//第一個進來時，questionOptionMap 沒東西，所以listInMap==null
					//listInMap==null 時，listInMap.addAll(optionList)會報錯
					if(listInMap==null) {
						//listInMap等於null就會新增一個ArrayList
						listInMap=new ArrayList<String>();
					}
					//再把當前的回答(選項)加入
					listInMap.addAll(optionList);
					//最後在整個加起來的結果放回到 questionOptionMap
					//Map: 相同的key,value 會後蓋前
					questionOptionMap.put(mapItem.getKey(),listInMap);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
//				new Statistics(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage()
//				,quizNum,countsLis);
	}
	
}
