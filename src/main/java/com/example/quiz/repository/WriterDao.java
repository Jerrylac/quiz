package com.example.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Writer;

@Repository
public interface WriterDao extends JpaRepository<Writer, Integer>{

	public List<Writer> findByQuizNum(int quizNum);
	
	public List<Writer> findByQuizNumOrderByNumDesc(int quizNum);
	
}
