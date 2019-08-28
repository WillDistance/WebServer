package com.study.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Web服务器
 * @author soft01
 *
 */
public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	
	
	public WebServer() throws IOException{
		
		try {
			System.out.println("启动服务器...");
			server=new ServerSocket(8088);
			threadPool=Executors.newFixedThreadPool(100);
			System.out.println("服务器启动成功！");
		} catch (IOException e) {
			throw e;
		}
	}
		
	public void start(){
		try {
			while(true){
				System.out.println("等待链接....");
				Socket socket=server.accept();
				ServerClientHandler client=new ServerClientHandler(socket);
				threadPool.execute(client);
			}	
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	
	public static void main(String[] args) {
		try {
			WebServer server=new WebServer();
			server.start();			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("服务器启动失败！");
		}	
	}	
}






















