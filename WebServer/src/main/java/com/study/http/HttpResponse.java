package com.study.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import com.study.common.HttpContext;

/**
 * 表示一个HTTP的响应
 * @author soft01
 *
 */
public class HttpResponse
{
    private OutputStream out;
    
    //状态代码
    private int          status;
    
    //响应头-ContentType
    private String       contentType;
    
    //响应头-ContentLength;
    private int          contentLength = -1;
    
    //响应实体
    private File         entity;
    
    //请求的协议版本
    private String       protocol;
    
    public HttpResponse(OutputStream out)
    {
        this.out = out;
    }
    
    /**
     *	将响应信息发送给客户端
     * @throws Exception 
     */
    public void flush() throws Exception
    {
        try
        {
            //1、发送状态行
            responseStatusLine();
            
            //2、发送响应头
            responseHeader();
            
            //3、响应正文
            responseEntity();
        }
        catch (Exception e)
        {
            System.out.println("响应客户端失败！");
            throw e;
        }
    }
    
    /**
     * 向客户端发送一行字符串，以CRLF结尾（CRLF自动追加）
     * @param line
     * @throws Exception 
     */
    private void println(String line) throws Exception
    {
        try
        {
            out.write(line.getBytes("ISO8859-1"));
            out.write(HttpContext.CR); //CR
            out.write(HttpContext.LF); //LF
        }
        catch (Exception e)
        {
            throw e;
        }
    }
    
    /**
     * 响应状态行
     * @return
     * @throws Exception 
     */
    private void responseStatusLine() throws Exception
    {
        try
        {
            String line = protocol + " " + status + " " + HttpContext.statusMap.get(status);
            println(line);
        }
        catch (Exception e)
        {
            System.out.println("发送响应状态行出错！");
            throw e;
        }
    }
    
    /**
     * 响应头
     * @return
     * @throws Exception 
     */
    private void responseHeader() throws Exception
    {
        try
        {
            if ( contentType != null )
            {
                String line = "Content-Type:" + contentType;
                println(line);
            }
            if ( contentLength >= 0 )
            {
                String line = "Comtent-Length:" + contentLength;
                println(line);
            }
            //单独发送CRLF表示响应头信息完毕
            println("");
        }
        catch (Exception e)
        {
            System.out.println("发送响应头出错！");
            throw e;
        }
    }
    
    /**
     * 响应正文
     * @return
     * @throws Exception 
     */
    private void responseEntity() throws Exception
    {
        /*
         * 将entity文件的所有字节发送给客户端
         */
        BufferedInputStream bis = null;
        try
        {
            bis = new BufferedInputStream(new FileInputStream(entity));
            BufferedOutputStream bos = new BufferedOutputStream(out);
            int len = -1;
            byte[] b = new byte[50];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
        }
        catch (Exception e)
        {
            System.out.println("发送响应正文出错！");
            throw e;
        }
        finally
        {
            try
            {
                if ( bis == null )
                {
                    bis.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
            
        }
        
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getContentType()
    {
        return contentType;
    }
    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    public int getContentLength()
    {
        return contentLength;
    }
    
    public void setContentLength(int contentLength)
    {
        this.contentLength = contentLength;
    }
    
    public File getEntity()
    {
        return entity;
    }
    
    public void setEntity(File entity)
    {
        this.entity = entity;
    }
    
    public String getProtocol()
    {
        return protocol;
    }
    
    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }
}
