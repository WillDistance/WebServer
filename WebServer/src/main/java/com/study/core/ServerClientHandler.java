package com.study.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.study.common.HttpContext;
import com.study.http.HttpRequest;
import com.study.http.HttpResponse;
import com.study.service.RegServlet;
import com.study.service.UpdateServlet;
import com.study.service.loginServlet;

/**
 * 
 * @描述: 服务端处理程序，处理请求
 * @版权: Copyright (c) 2019 
 * @公司: 思迪科技 
 * @作者: 严磊
 * @版本: 1.0 
 * @创建日期: 2019年8月28日 
 * @创建时间: 下午2:33:39
 */
public class ServerClientHandler implements Runnable
{
    private Socket socket; //服务端的socket
                           
    /**
     * 
     * 描述： 构造方法
     * @param socket
     */
    public ServerClientHandler(Socket socket)
    {
        this.socket = socket;
    }
    
    public void run()
    {
        try
        {
            //获取socket的输入流。接收客户端发送过来的请求信息
            InputStream in = socket.getInputStream();
            
            //根据输入流，创建对应的请求对象
            HttpRequest request = new HttpRequest(in);
            
            //获取socket的输出流。给客户端发送响应信息
            OutputStream out = socket.getOutputStream();
            
            //创建对应的响应对象
            HttpResponse response = new HttpResponse(out);
            
            /*
             * 处理用户请求
             */
            String uri = request.getUri(); //获取用户请求资源路径
            String protocol = request.getProtocol(); //获取请求的协议版本
            if ( "/".equals(uri) )
            {
                //去首页
                File file = new File("webapp" + File.separator + "index.html");
                responseFile(HttpContext.STATUS_CODE_OK, protocol, file, response);
                
            }
            else
            {
                File file = new File("webapp" + uri);//拼接后webapp/index.html
                /**
                 * 也就是说如果通过Httprequest解析客户端发送的内容后：
                 * 1、uri=index.html|reg.html|login.html中的一个，那就直接给用户发送响应的网页信息
                 * 2、文件不存在  1：uri=/reg|/login 说明客户端不是请求的一个界面，而是要进行注册或登录
                 *           2：请求错误，发送没有资源的网页给客户端
                 * 
                 */
                if ( file.exists() )
                {
                    System.out.println("找到了相应的资源");
                    /*
                     * 响应页面要向用户发送的内容：
                     * Http/1.1 200 OKCRLF    //状态行
                     *
                     * Content-type:text/htmlCRLF //响应头
                     * Content-Length:273CRLF
                     * CRLF
                     * 
                     * 101010100010010101010 
                     */
                    responseFile(HttpContext.STATUS_CODE_OK, protocol, file, response);
                    
                }
                else if ( "/reg".equals(uri) )
                {
                    //注册
                    RegServlet servlet = new RegServlet();
                    servlet.service(request, response);
                    
                }
                else if ( "/login".equals(uri) )
                {
                    //登录
                    loginServlet login = new loginServlet();
                    login.service(request, response);
                    
                }
                else if ( "/change".equals(uri) )
                {
                    //修改
                    UpdateServlet servlet = new UpdateServlet();
                    servlet.service(request, response);
                }
                else
                {
                    System.out.println("没有资源：404");
                    file = new File("webapp" + File.separator + "404.html");
                    responseFile(HttpContext.STATUS_CODE_NOTFOUND, protocol, file, response);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("解析出现错误！");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }
    
    /**
     * 根据给定的文件分析其名字后缀以获取对应的ContenType
     */
    private String getContentTypeByFile(File file)
    {
        //获取文件名
        String name = file.getName();
        System.out.println("文件名：" + name);
        
        //截取后缀
        String ext = name.substring(name.lastIndexOf(".") + 1, name.length());
        System.out.println("后缀：" + ext);
        
        //获取对应的ContentType
        String contentType = HttpContext.contentTypeMapping.get(ext);
        System.out.println("contentType:" + contentType);
        return contentType;
    }
    
    /**
     * 响应客户端指定资源
     * @param status  响应状态码
     * @param file  要响应的资源
     * @throws Exception 
     */
    private void responseFile(int status, String protocol, File file, HttpResponse response) throws Exception
    {
        //1 设置状态行信息
        response.setStatus(status); //HttpContext.STATUS_CODE_NOTFOUND
        response.setProtocol(protocol);//设置协议版本
        //2 设置响应头信息	
        response.setContentType(getContentTypeByFile(file));//分析该文件后缀，根据后缀获取对应的ContenType
        response.setContentLength((int) file.length());
        
        //3 设置响应正文
        response.setEntity(file);
        
        //4 响应客户端
        response.flush();
    }
    
}

/*OutputStreamWriter osw=new OutputStreamWriter(out);
PrintWriter pw=new PrintWriter(osw);
pw.print(request.getProtocol()+"200"+"OKCRLF");//request.getProtocol()+"200"+"OKCRLF"
String[] arr=uri.split("\\.");
pw.print("Content-type:text/"+arr[1]+"CRLF");

pw.print("Content-Length:"+file.length()+"CRLF");

pw.print("CRLF");
System.out.println(1);

int d;
byte[] len=new byte[40];
FileInputStream fis=new FileInputStream(file);
while((d=fis.read(len))!=-1){
	System.out.println(2);
	out.write(len,0,d);
	
}*/

/*//请求行
String line=readLine(in);
System.out.println(line);
//读取若干行（直到单独读取了CRLF）。消息头
*/

/*InputStreamReader isr=new InputStreamReader(in,"utf-8");
BufferedReader br=new BufferedReader(isr);
String message=null;
while((message=br.readLine())!=null){
	System.out.println(message);
}
while(true){
	String line=readLine(in);
	System.out.println(line);
}*/
