package com.study.service;

import com.study.core.HttpServlet;
import com.study.dao.UserInfoDAO;
import com.study.entities.UserInfo;
import com.study.http.HttpRequest;
import com.study.http.HttpResponse;



/**
 * 用来完成用户注册功能
 * @author soft01
 *
 */
public class RegServlet extends HttpServlet{
	
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("开始处理注册");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String nickname=request.getParameter("nickname");
		
		UserInfoDAO dao=new UserInfoDAO();
	
		if ((dao.findByUsername(username))==null) {
			//创建一个UserInfo实例用于表示该注册信息
			UserInfo userInfo=new UserInfo();
			
			userInfo.setUsername(username);
			userInfo.setPassword(password);
			userInfo.setNickname(nickname);
			userInfo.setAccount(5000);

			// 保存用户信息
			boolean success = dao.save(userInfo);
			if (success) {
				// 注册成功
				forward("/reg_ok.html", response);
			} else {
				// 注册失败
				forward("/reg_not.html", response);
			}
		}else{
			// 用户已存在
			forward("/reg_not.html", response);
		}
	}
}
