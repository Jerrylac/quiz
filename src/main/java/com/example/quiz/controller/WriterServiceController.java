package com.example.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.WriterService;
import com.example.quiz.vo.QuizRes;
import com.example.quiz.vo.StatisticsRes;
import com.example.quiz.vo.WriterGetRes;
import com.example.quiz.vo.WriterReq;

@CrossOrigin
@RestController
public class WriterServiceController {

	@Autowired
	private WriterService writerService;
	
	@PostMapping(value = "write/write")
	public QuizRes write(@RequestBody WriterReq req) {
		return writerService.write(req);
	}
	
	@PostMapping(value = "write/findbyquizNum")
	public WriterGetRes findByQuizNum(@RequestParam int quizNum) {
		return writerService.findByQuizNum(quizNum);
	}
	
	@PostMapping(value = "write/count")
	public StatisticsRes count(@RequestParam int quizNum) {
		return writerService.count(quizNum);
	}
}
