package com.example.quiz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.service.ifs.UserService;
import com.example.quiz.vo.UserLoginReq;
import com.example.quiz.vo.UserLoginRes;

@CrossOrigin
@RestController
//@CrossOrigin(origins = "https://localhost:8080/api/login")
public class UserServiceController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="api/login")
	public UserLoginRes login(@RequestBody UserLoginReq req, HttpSession session) {
		//已登入成功張浩，表示資訊已存在 session 中，可以跳過 userService.login 的檢查
		String attr=(String) session.getAttribute("account");
		//除了確認 session 中有資訊外，還確認 session 與 req 中的帳號是否一樣
		if(StringUtils.hasText(attr)&&attr.equals(req.getAccount())) {
			return new UserLoginRes(RtnCode.SUCCESSFUL);
		}
		UserLoginRes res=userService.login(req.getAccount(), req.getPwd());
		if(res.getRencode().getCode()==200) {
			session.setAttribute("account", req.getAccount());
			//預設有效時間為30秒 
			//設定 session 有效時間, 單位:秒
			session.setMaxInactiveInterval(600);
		}
		return res;
	}
	
	@GetMapping(value="api/logout")
	public UserLoginRes logout(HttpSession session) {
		//讓session失效
		session.invalidate();
		return new UserLoginRes(RtnCode.SUCCESSFUL);
	}
}
