package com.example.quiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.DeleteQuizReq;
import com.example.quiz.vo.QuizGetRes;
import com.example.quiz.vo.QuizReq;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.QuizSearchReq;

@CrossOrigin
@RestController
public class QuizServiceController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping(value = "quiz/create")
	public QuizRes create(@RequestBody QuizReq req, HttpSession session) {
//		String attr= (String) session.getAttribute("account");
//		if(!StringUtils.hasText(attr)) {
//			return new QuizRes(RtnCode.PLEASE_LOGIN_FIRST.getCode(), RtnCode.PLEASE_LOGIN_FIRST.getMessage());
//		}
		return quizService.create(req.getName(), req.getDescription(), req.getStartData(), req.getEndDate()
				, req.getQuestionList(), req.isPublished());
	}
	
	//�]������ @RequestParam , api �� uri �|�Oquiz/update?quiz_num=�Ʀr
	//@RequestParam �ѼƤ����w�q value ,��ܥL�|���� quiz_num ���r�굥���᭱����,�Y�S�[,�w�]�N�O�ܼƦW��num
	@PostMapping(value = "quiz/update")
	public QuizRes upDate(@RequestParam(value = "quiz_num") int num , @RequestBody QuizReq req) {
		return quizService.upDate(num, req.getName(), req.getDescription(), req.getStartData(), req.getEndDate()
				, req.getQuestionList(), req.isPublished());
	}
	
	//�]���u���@�ӽШD�Ѽ�,�ҥH��ĳ�ϥΦ���k
	//�]��@RequestBody ���ѼƬO List,uri �|�O quiz/delet_quiz?quiz_num_list=100,200,300
	//����Τ��A��
	@PostMapping(value = "quiz/delete")
	public QuizRes deleteQuiz(@RequestBody DeleteQuizReq req) {
		return quizService.deleteQuiz(req.getNumlList());
	}
	
	//@RequestParam ���ѼƦ��h��,uri���Ѽƭn��&�걵:uri �|�Odelete_question?quiz_num=�s��&question_num_list=�s��1,�s��2
	@PostMapping(value = "quiz/delete_question")
	public QuizRes deleteQuestion(@RequestParam(value = "quiz_num")int quizNum,
			@RequestParam(value = "question_num_list")List<Integer> numlList) {
		return quizService.deleteQuestion(quizNum, numlList);
	}
	
	//uri���h�ӰѼ� �|�Odelete_question?quiz_num=�s��&question_num_list=�s��1,�s��2
	//@RequestParam �� Map ��, quiz_num=�s��, quiz_num �|�O map ���� key ,�s���|�O map ���� value ,��l�H������
	@SuppressWarnings("unchecked")
	@PostMapping(value = "quiz/delete_question_1")
	public QuizRes deleteQuestion1(@RequestParam Map<String, Object> paramMap) {
		int quizNum= (int) paramMap.get("quiz_num");
		List<Integer> numlList=(List<Integer>) paramMap.get("question_num_list");
		return quizService.deleteQuestion(quizNum, numlList);
	}
	
	@PostMapping(value = "quiz/search")
	public QuizGetRes search(@RequestBody QuizSearchReq req) {
		return quizService.search(req.getQuizName(), req.getStartDate(), req.getEndDate(), req.isLongin());
	}
}
