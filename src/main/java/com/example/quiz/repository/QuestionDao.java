package com.example.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Question1;
import com.example.quiz.entity.Question1Id;

@Repository
public interface QuestionDao extends JpaRepository<Question1, Question1Id> {

}
