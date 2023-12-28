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
	
	//因為有用 @RequestParam , api 的 uri 會是quiz/update?quiz_num=數字
	//@RequestParam 參數中有定義 value ,表示他會取到 quiz_num 此字串等號後面的值,若沒加,預設就是變數名稱num
	@PostMapping(value = "quiz/update")
	public QuizRes upDate(@RequestParam(value = "quiz_num") int num , @RequestBody QuizReq req) {
		return quizService.upDate(num, req.getName(), req.getDescription(), req.getStartData(), req.getEndDate()
				, req.getQuestionList(), req.isPublished());
	}
	
	//因為只有一個請求參數,所以建議使用次方法
	//因為@RequestBody 的參數是 List,uri 會是 quiz/delet_quiz?quiz_num_list=100,200,300
	//不能用中括號
	@PostMapping(value = "quiz/delete")
	public QuizRes deleteQuiz(@RequestBody DeleteQuizReq req) {
		return quizService.deleteQuiz(req.getNumlList());
	}
	
	//@RequestParam 的參數有多個,uri的參數要用&串接:uri 會是delete_question?quiz_num=編號&question_num_list=編號1,編號2
	@PostMapping(value = "quiz/delete_question")
	public QuizRes deleteQuestion(@RequestParam(value = "quiz_num")int quizNum,
			@RequestParam(value = "question_num_list")List<Integer> numlList) {
		return quizService.deleteQuestion(quizNum, numlList);
	}
	
	//uri有多個參數 會是delete_question?quiz_num=編號&question_num_list=編號1,編號2
	//@RequestParam 用 Map 接, quiz_num=編號, quiz_num 會是 map 中的 key ,編號會是 map 中的 value ,其餘以此類推
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
