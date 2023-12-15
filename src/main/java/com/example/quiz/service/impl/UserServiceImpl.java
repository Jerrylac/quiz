package com.example.quiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.repository.UserDao;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginRes;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao uesrDao;
	
	@Override
	public UserLoginRes login(String account, String pwd) {
		if(!StringUtils.hasText(account)||!StringUtils.hasText(pwd)) {
			return new UserLoginRes(RtnCode.PARAM_ERROR);
		}
//		User res =uesrDao.findByAccountAndPwd(account, pwd);
//		if(res==null) {
//			return;
//		}
//		return;
		boolean res=uesrDao.existsByAccountAndPwd(account, pwd);
		
		if(!res) {//!res µ¥¦P©ó res==false
			return new UserLoginRes(RtnCode.ACCOUNT_NOT__FOUND);
		}
		return new UserLoginRes(RtnCode.SUCCESSFUL);
		
	}

}
