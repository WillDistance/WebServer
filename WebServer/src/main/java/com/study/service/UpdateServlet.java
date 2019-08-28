package com.study.service;

import com.study.core.HttpServlet;
import com.study.dao.UserInfoDAO;
import com.study.entities.UserInfo;
import com.study.http.HttpRequest;
import com.study.http.HttpResponse;

/**
 * 完成用户修改信息的请求
 * @author soft01
 *
 */
public class UpdateServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		/*
		 * 1:获取用户输入的用户名等信息
		 * 2：先判断该用户是否存在，不存在则提示用户（跳转没有该用户的提示页面）
		 * 3：将用户输入的信息存入一个UserInfo实例中
		 * 4：调用UserInfoDAO的update进行修改
		 * 5:若修改成功，跳转修改成功的提示页面，否则跳转失败的提示页面
		 */
		
		System.out.println("开始处理修改用户信息");
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		String nickname= request.getParameter("nickname");
		int account= Integer.parseInt(request.getParameter("account"));
		
		UserInfoDAO dao=new UserInfoDAO();
		UserInfo userinfo =new UserInfo(0,username,password,nickname,account);
		if(dao.update(userinfo)){
			//修改成功
			System.out.println("修改成功");
			/*
			 * 响应修改成功的界面给客户端
			 */
			forward("/change_ok.html", response);
			
		}else{
			//修改失败
			System.out.println("修改失败");
			/*
			 * 响应修改失败的界面给客户端
			 */
			forward("/change_not.html", response);
			
		}

	}

	

	
}
