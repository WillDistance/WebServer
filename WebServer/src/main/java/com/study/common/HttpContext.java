package com.study.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @描述: HTTP协议响应相关定义
 * @版权: Copyright (c) 2019 
 * @公司: 思迪科技 
 * @作者: 严磊
 * @版本: 1.0 
 * @创建日期: 2019年8月28日 
 * @创建时间: 下午2:28:01
 */
public class HttpContext
{
    public static final int                  CR                     = 13;
    
    public static final int                  LF                     = 10;
    
    /*
     * 状态码定义
     */
    //状态码-接受成功
    public static final int                  STATUS_CODE_OK         = 200;
    
    //状态描述-接受成功
    public static final String               STATUS_REASON_OK       = "OK";
    
    //状态码-资源未发现
    public static final int                  STATUS_CODE_NOTFOUND   = 404;
    
    //状态描述-资源未发现
    public static final String               STATUS_REASON_NOTFOUND = "Not Found";
    
    //状态码-服务器发生错误
    public static final int                  STATUS_CODE_ERROR      = 500;
    
    //状态码-服务器发生未知错误
    public static final String               STATUS_REASON_ERROR    = "Internal Server Error";
    
    /*
     * 状态码-状态描述 对应的Map
     */
    public static final Map<Integer, String> statusMap              = new HashMap<Integer, String>();
    
    /*
     * Content-Type映射Map
     * key：资源类型（资源文件的后缀名）
     * value：对应资源在HTTP协议中规定的ContentType
     * 
     * 例如：index.html
     * 那么这个文件在该Map中对应key应当是html
     * value对应的值就是text/html
     */
    public static final Map<String, String>  contentTypeMapping     = new HashMap<String, String>();
    
    /**
     * 根据配置文件初始化相关信息 
     */
    static
    {
        //1、初始化ContentType映射
        initContentTypeMapping();
        
        //2、初始化状态码-状态描述
        initStatus();
    }
    
    /**
     * 
     * @描述：初始化状态码-状态描述
     * @作者：严磊
     * @时间：2019年8月28日 下午2:25:32
     */
    private static void initStatus()
    {
        statusMap.put(STATUS_CODE_OK, STATUS_REASON_OK);
        statusMap.put(STATUS_CODE_NOTFOUND, STATUS_REASON_NOTFOUND);
        statusMap.put(STATUS_CODE_ERROR, STATUS_REASON_ERROR);
    }
    
    /**
     * 
     * @描述：初始化ContentType映射
     * @作者：严磊
     * @时间：2019年8月28日 下午2:25:24
     */
    private static void initContentTypeMapping()
    {
        /*
         * 将web.xml配置文件中<type-mappings>中的每一个<type-mapping>进行解析，将其中
         * 属性ext的值作为key，将type属性的值作为value存入到contentTypeMapping这个Map中。
         */
        System.out.println("初始化contentType");
        try
        {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new FileInputStream("conf" + File.separator + "web.xml"));
            Element type = doc.getRootElement().element("type-mappings");
            List<Element> weblist = type.elements();
            for (Element e : weblist)
            {
                String ext = e.attributeValue("ext");
                String typ = e.attributeValue("type");
                contentTypeMapping.put(ext, typ);
            }
            System.out.println(contentTypeMapping);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
