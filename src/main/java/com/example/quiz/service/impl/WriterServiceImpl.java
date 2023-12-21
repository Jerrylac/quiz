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
		//��answer���r���নmap
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
			//�ˬd������D�O�_������
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
		//���Ͱ��D�s���P�ﶵ List ��Map
		Map<Integer, List<String>> questionOptionMap=new HashMap<>();
		for (Writer item : res) {
			//[{"qNum":1,"optionList":["BBB"]},{"qNum":2,"optionList":["CCC"]},{"qNum":3,"optionList":["DDD"]}]
			String answerStr=item.getAnswer();
			try {
				List<Answer> ansList= mapper.readValue(answerStr, new TypeReference<>() {});
				for (Answer ans : ansList) {
					if(questionOptionMap.containsKey(ans.getqNum())) {
						//�� key ������ value ���X
						List<String> listInMap = questionOptionMap.get(ans.getqNum());
						//�N ans ���� OptionList �W�[��쥻�w�s�b map ���� option list
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
		//�p��C�Ӱ��D���C�ӿﶵ����
		List<Count> countList= new ArrayList<>();
		//		   <���D�s��, �ﶵ��List>								  �Omap�̧�key value
		for (Entry<Integer, List<String>> mapItem : questionOptionMap.entrySet()) {
			Count count=new Count();
			//�p��C�D�C�ӿﶵ������
			//map<�ﶵ���D ��ܦ���>
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
	
	
	//�쥻��write
	public QuizRes write1(WriterReq req) {
		if(req.getQuizNum()<=0||!StringUtils.hasText(req.getName())) {
			return new QuizRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		if(!StringUtils.hasText(req.getAnswer())) {
			return new QuizRes(RtnCode.NO_QUESTION_ANSWER.getCode(), RtnCode.NO_QUESTION_ANSWER.getMessage());
		}
		//��answer���r���নmap
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
			//�ˬd������D�O�_������
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
	
	//�쥻��count
	public Statistics count1(int quizNum) {
		if(quizNum<=0) {
			return new Statistics(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage()
					,quizNum,null);
		}
		List<Writer> res = writerDao.findByQuizNum(quizNum);
		//���Ͱ��D�s���P�ﶵ List ��Map
		Map<String, List<String>> questionOptionMap=new HashMap<>();
		for (Writer item : res) {
			String answerStr=item.getAnswer();
			try {
				//	���D�s��,�ﶵ  -->�ﶵ���h�Ӯ�,�γr���걵
				Map<String,String> answerMap = mapper.readValue(answerStr, Map.class);
				for ( Entry<String, String> mapItem : answerMap.entrySet()) {
					String valueStr=mapItem.getValue();
					valueStr = StringUtils.trimAllWhitespace(valueStr);
					String[] array=valueStr.split(",");
					List<String> optionList=new ArrayList<>();
					optionList.addAll(Arrays.asList(array));
					//�N���e���Ҧ��^��(�ﶵ)�q questionOptionMap ���X
					List<String> listInMap=questionOptionMap.get(mapItem.getKey());
					//�Ĥ@�Ӷi�ӮɡAquestionOptionMap �S�F��A�ҥHlistInMap==null
					//listInMap==null �ɡAlistInMap.addAll(optionList)�|����
					if(listInMap==null) {
						//listInMap����null�N�|�s�W�@��ArrayList
						listInMap=new ArrayList<String>();
					}
					//�A���e���^��(�ﶵ)�[�J
					listInMap.addAll(optionList);
					//�̫�b��ӥ[�_�Ӫ����G��^�� questionOptionMap
					//Map: �ۦP��key,value �|��\�e
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
