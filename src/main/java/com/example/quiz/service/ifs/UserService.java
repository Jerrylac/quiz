package com.example.quiz.service.ifs;

import com.example.quiz.vo.UserLoginRes;

public interface UserService {

	public UserLoginRes login(String account ,String pwd);
}
