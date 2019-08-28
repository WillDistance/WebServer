package com.study.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 
 * @描述: 数据库连接的管理类
 * @版权: Copyright (c) 2019 
 * @公司: 思迪科技 
 * @作者: 严磊
 * @版本: 1.0 
 * @创建日期: 2019年8月28日 
 * @创建时间: 下午5:00:27
 */
public class DBUtil
{
    private static BasicDataSource dataSource;
    
    static
    {
        try
        {
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            String driverclass = prop.getProperty("driverclass");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            int maxActive = Integer.parseInt(prop.getProperty("maxactive"));
            int maxWait = Integer.parseInt(prop.getProperty("maxwait"));
            
            dataSource = new BasicDataSource();
            
            //class.forName(....)
            dataSource.setDriverClassName(driverclass);
            
            //DriverManager.getConnection(.....)
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            
            //设置最大链接数
            dataSource.setMaxActive(maxActive);
            //设置最大等待时间
            dataSource.setMaxWait(maxWait);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取一个数据库的链接
     * @return
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        try
        {
            /*
             * 向链接池获取链接
             * 若链接池中没有可用链接时，该方法会阻塞当前线程，阻塞时间有链接池设置的maxWait决定。
             * 当阻塞过程中连接池有了可用连接时会立即将连接返回。若超时仍然没有可用连接时，该方法会抛出异常。
             */
            return dataSource.getConnection();
            
        }
        catch (SQLException e)
        {
            System.out.println("数据库连接失败！");
            throw e;
        }
        
    }
    
    public static void closeConnection(Connection conn)
    {
        try
        {
            if ( conn != null )
                conn.setAutoCommit(true); //设置为默认的自动提交
            /*
             * 连接池的连接对于close方法的处理是将连接在连接池中的状态设置为空闲而非真的将其关闭
             */
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
