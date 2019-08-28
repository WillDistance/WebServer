package com.study.core;

import java.io.File;

import com.study.common.HttpContext;
import com.study.http.HttpResponse;

public class HttpServlet {
	public void forward(String uri,HttpResponse response){
		/*
		 * 响应注册成功的界面给客户端
		 */
		File file1=new File("webapp"+uri);
		//设置状态行
		response.setStatus(HttpContext.STATUS_CODE_OK);
		response.setProtocol("HTTP/1.1");//设置协议版本
		//设置响应头
		response.setContentType("text/html");
		response.setContentLength((int)file1.length());
		//设置响应正文
		response.setEntity(file1);
		try {
			response.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
