package com.example.quiz.service.ifs;

import java.time.LocalDate;

import com.example.quiz.vo.QuizRes;

public interface QuizService {

	public QuizRes create(String name,String description,LocalDate startData,LocalDate endDate);
}
