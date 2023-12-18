package com.example.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Writer;

@Repository
public interface WriterDao extends JpaRepository<Writer, Integer>{

}
