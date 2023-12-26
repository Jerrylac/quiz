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
		//�w�n�J���\�i�E�A��ܸ�T�w�s�b session ���A�i�H���L userService.login ���ˬd
		String attr=(String) session.getAttribute("account");
		//���F�T�{ session ������T�~�A�ٽT�{ session �P req �����b���O�_�@��
		if(StringUtils.hasText(attr)&&attr.equals(req.getAccount())) {
			return new UserLoginRes(RtnCode.SUCCESSFUL);
		}
		UserLoginRes res=userService.login(req.getAccount(), req.getPwd());
		if(res.getRencode().getCode()==200) {
			session.setAttribute("account", req.getAccount());
			//�w�]���Įɶ���30�� 
			//�]�w session ���Įɶ�, ���:��
			session.setMaxInactiveInterval(600);
		}
		return res;
	}
	
	@GetMapping(value="api/logout")
	public UserLoginRes logout(HttpSession session) {
		//��session����
		session.invalidate();
		return new UserLoginRes(RtnCode.SUCCESSFUL);
	}
}
