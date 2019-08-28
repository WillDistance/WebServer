package com.study.service;

import com.study.core.HttpServlet;
import com.study.dao.UserInfoDAO;
import com.study.entities.UserInfo;
import com.study.http.HttpRequest;
import com.study.http.HttpResponse;

public class loginServlet extends HttpServlet{

	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("开始处理登录");
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		
		UserInfoDAO dao=new UserInfoDAO();
		UserInfo userinfo =new UserInfo();
		if(dao.findByUsernameAndPasswprd(username, password)!=null){
			//登录成功
			System.out.println("登录成功");
			/*
			 * 响应登录成功的界面给客户端
			 */
			forward("/login_ok.html", response);
			
		}else{
			//登录失败
			System.out.println("登录失败");
			/*
			 * 响应登录失败的界面给客户端
			 */
			forward("/login_not.html", response);
		}
	}
}
