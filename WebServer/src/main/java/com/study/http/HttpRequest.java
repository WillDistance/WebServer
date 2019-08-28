package com.study.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.study.common.HttpContext;

/**
 * 
 * @描述: Http请求对象   封装一个Http请求相关信息
 * @版权: Copyright (c) 2019 
 * @公司: 思迪科技 
 * @作者: 严磊
 * @版本: 1.0 
 * @创建日期: 2019年8月28日 
 * @创建时间: 下午2:58:44
 */
public class HttpRequest
{
    //请求方法
    private String              method;
    
    //请求资源URI (URI统一资源定位)
    private String              uri;
    
    //请求协议版本
    private String              protocol;
    
    //消息报头信息
    private Map<String, String> header;
    
    //存储客户端传递过来的参数
    private Map<String, String> parameters;
    
    public HttpRequest(InputStream in) throws Exception
    {
        header = new HashMap<String, String>();
        parameters = new HashMap<String, String>();
        try
        {
            //1、解析请求行
            parseRequestLine(in);
            //2、解析消息头
            parseRequestHeader(in);
            //3、解析消息正文
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    /**
     * 解析请求行信息
     * @param in
     * @throws Exception 
     */
    private void parseRequestLine(InputStream in) throws Exception
    {
        /*
         * 实现步骤：
         * 1：先读取一行字符串（CRLF结尾）
         * 2：根据空格拆分(\s)
         * 3：将请求行中三项内容设置到HttpRequest对应属性上
         * GET /index.html HTTP/1.1
         */
        try
        {
            System.out.println("解析请求行信息");
            //请求行
            String line = readLine(in);
            //对请求行格式做一些必要的验证
            if ( line.length() == 0 )
            {
                throw new RuntimeException("空的请求行");
            }
            String[] arr = line.split("\\s");
            this.method = arr[0];
            //this.uri=arr[1];
            parseUri(arr[1]);
            this.protocol = arr[2];
            
        }
        catch (Exception e)
        {
            throw e;
        }
        System.out.println("解析请求行信息完毕！");
    }
    
    /**
     * 处理URI
     * @param Uri
     */
    private void parseUri(String Uri)
    {
        /*
         * /index.html
         * /reg?username=fancq&password=123456&nickname=fanfan
         * 在GET请求中 ，URI可能会有上面两种情况。
         * HTTP协议规定，GET请求中的URI可以传递参数，而规定是请求的资源后面以“？”分隔，之后则为所有要传递的参数，每个参数由:name=value
         * 的格式保存，参数之间使用“&”分隔
         * 这里的处理要求：
         * 将“？”之前的内容保存到属性uri上。之后的每个参数都存入属性parameters中，其中key为参数名，value为参数值
         * 1:实例化HashMap用于初始化属性parameters
         * 
         */
        
        int index = -1;
        if ( (index = Uri.indexOf("?")) >= 0 )
        { //说明存在参数
            String[] uriArr = Uri.split("\\?");
            uri = uriArr[0];
            String[] arr = uriArr[1].split("&");
            for (int i = 0; i < arr.length; i++)
            {
                String[] paraDate = arr[i].split("=");
                if ( paraDate.length > 0 )
                {
                    this.parameters.put(paraDate[0], paraDate[1]);
                }
                else
                {
                    this.parameters.put(paraDate[0], null);
                }
            }
        }
        else
        {
            uri = Uri;
        }
    }
    
    /**
     * 解析消息头
     * @param in
     * @throws IOException 
     */
    private void parseRequestHeader(InputStream in) throws IOException
    {
        System.out.println("解析消息头");
        /*
         * 消息头由若干行组成
         * 每行格式为： name：valueCRLF
         * 当所有消息全部发送过来后，浏览器会单独发送一个CRLF结束
         * 
         * 实现思路：
         * 1：死循环下面步骤
         * 2：读取一行字符串
         * 3：判断该字符串是否为空串，若是空串说明读到最后单独的CRLF，那么就可以停止循环，
         * 	  不用再解析消息头信息
         * 4：若不是空串，则按照“：”截取出名字 
         */
        while (true)
        {
            try
            {
                String line = readLine(in);
                if ( line.length() == 0 )
                {
                    break;
                }
                String key = line.substring(0, line.indexOf(":"));
                String value = line.substring(line.indexOf(":") + 1, line.length()).trim();
                System.out.println(key + value);
                header.put(key, value);
            }
            catch (IOException e)
            {
                throw e;
            }
        }
        
        System.out.println("解析消息头完毕！");
    }
    
    /**
     * 根据输入流读取一行字符串
     * 根据HTTP协议读取请求中的一行内容，以CRLF结尾的一行字符串
     * @param in
     * @return
     * @throws IOException
     */
    private String readLine(InputStream in) throws IOException
    {
        // 请求中第一行字符串（请求行内容）
        StringBuilder builder = new StringBuilder();
        /*
         * 连续读取若干字符，直到连续读取到了CR(13) LF(10)为止
         * c1是本次读到的字符，c2是上次读取到的字符
         */
        int c1 = -1, c2 = -1;
        while ((c1 = in.read()) != -1)
        {
            if ( c1 == HttpContext.LF && c2 == HttpContext.CR )
            {
                break;
            }
            builder.append((char) c1);
            c2 = c1;
        }
        return builder.toString().trim();
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public String getUri()
    {
        return uri;
    }
    
    public String getProtocol()
    {
        return protocol;
    }
    
    public Map<String, String> getHeader()
    {
        return header;
    }
    
    /*
     * 根据参数名获取参数值
     * username=fancq&password=123456&nickname=fanfan
     */
    public String getParameter(String name)
    {
        return parameters.get(name);
    }
    
}
