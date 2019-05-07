package com.zhpx.springbootdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, HttpServletResponse response){

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
		try{
			//使用SpringSecurity拦截登陆请求 进行认证和授权
			Authentication authenticate = authenticationManager.authenticate(token);

			SecurityContextHolder.getContext().setAuthentication(authenticate);
			//使用redis session共享
			HttpSession session = request.getSession();
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
		}catch (Exception e){
			e.printStackTrace();
			return "redirect:login-error?error=2";
		}
		System.out.println("用户来登录了");
		return "/user";



	}

	@RequestMapping(value="/info")
	public void userInfo(ServletRequest request, ServletResponse response){
		System.out.println("获取用户信息=========");
	}


	@RequestMapping(value="/add")
	public void addUser(ServletRequest request, ServletResponse response){
		System.out.println("新增用户==============");
	}
}
