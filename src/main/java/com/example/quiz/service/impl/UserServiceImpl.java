package com.example.quiz.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.User;
import com.example.quiz.repository.UserDao;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginRes;

@Service
public class UserServiceImpl implements UserService{
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

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
		//檢查帳號是否存在
		Optional<User> op = uesrDao.findById(account);
		if(op.isEmpty()) {
			return new UserLoginRes(RtnCode.ACCOUNT_NOT__FOUND);
		}
		User user = op.get();
		if(!encoder.matches(pwd, user.getPwd())) {
			return new UserLoginRes(RtnCode.ACCOUNT_NOT__FOUND);
		}
		return new UserLoginRes(RtnCode.SUCCESSFUL);
		
	}

	@Override
	public UserLoginRes create(String account, String pwd) {
		if(!StringUtils.hasText(account)||!StringUtils.hasText(pwd)) {
			return new UserLoginRes(RtnCode.PARAM_ERROR);
		}
		if(uesrDao.existsById(account)){
			return new UserLoginRes(RtnCode.ACCOUNT_EXISTED);
		}
		uesrDao.save(new User(account,encoder.encode(pwd)));
		return new UserLoginRes(RtnCode.SUCCESSFUL);
	}

}
