package com.study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.study.db.DBUtil;
import com.study.entities.UserInfo;

/**
 * DAO 数据连接对象
 * DAO是一个层次，该层里的所有类都是和数据库打交道的，作用是将数据操作的功能从业务逻辑层中分离出来，
 * 使得业务逻辑层更专注的处理业务操作，而对于数据的维护操作分离到DAO中。并且DAO与业务逻辑层之间是用
 * JAVA中的对象来传递数据，这也使得有了DAO可以让业务逻辑层对数据的操作完全面向对象化
 * @author soft01
 *
 */
public class UserInfoDAO {
	/**
	 * 根据给定的用户名查找该用户
	 * @param username
	 * @return 若没有此用户则返回值为NULL
	 */
	public UserInfo findByUsername(String username){
		/*
		 * 根据给定的用户名查询该用户信息，若没有记录则直接返回NULL，若查询到将该条记录，将各个字段的值
		 * 取出来存入到一个UserInfo实例中并返回
		 */
		Connection conn=null;
		try{
		conn=DBUtil.getConnection();
		String sql="SELECT id,username,password,nickname,account FROM userinfo_yl WHERE username=?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs=ps.executeQuery();
		if(rs.next()){
			int id=rs.getInt("id");
			username=rs.getString("username");
			String password=rs.getString("password");
			String nickname=rs.getString("nickname");
			int account=rs.getInt("account");
			return new UserInfo(id,username,password,nickname,account);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(conn);
		}
		return null;
	}
	
	
	/**
	 * 保存给定的UserInfo对象所表示的用户信息
	 */
	public boolean save(UserInfo userinfo){
		Connection conn=null;
		String sql="INSERT INTO userinfo_yl"+
					"(id,username,password,nickname,account) "+
					"VALUES "+
					"(seq_userinfo_id_yl.NEXTVAL,?,?,?,?) ";
		try {
			conn=DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql,new String[]{"id"});
			ps.setString(1, userinfo.getUsername());
			ps.setString(2,userinfo.getPassword());
			ps.setString(3, userinfo.getNickname());
			ps.setInt(4, userinfo.getAccount());
			int d=ps.executeUpdate();
			if(d>0){
				//插入数据成功
				ResultSet rs=ps.getGeneratedKeys();
				rs.next();
				int id=rs.getInt(1);
				userinfo.setId(id);
				return true;
			}
		} catch (SQLException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public UserInfo findByUsernameAndPasswprd(String username,String password){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT id,username,password,nickname,account FROM userinfo_yl "+
						"WHERE username=? "+
						"AND password=? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				int id=rs.getInt("id");
				username=rs.getString("username");
				password=rs.getString("password");
				String nickname=rs.getString("nickname");
				int account=rs.getInt("account");
				return new UserInfo(id,username,password,nickname,account);
			}
		} catch (Exception e) {
			
		}finally{
			DBUtil.closeConnection(conn);
		}
		return null;
	}
	
	
	/**
	 * 修改用户信息
	 * @param args
	 */
	public boolean update(UserInfo userinfo){
		/*
		 * 名字和ID不可修改，可以根据用户名修改userInfo中该用户的新密码昵称以及余额
		 * UPDATE userinfo
		 * SET password=?,nickname=?,account=?
		 * WHERE username=?
		 */
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="UPDATE userinfo_yl "+
						"SET password=?,nickname=?,account=? "+
						"WHERE username=? ";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, userinfo.getPassword());
			ps.setString(2, userinfo.getNickname());
			ps.setInt(3, userinfo.getAccount());
			ps.setString(4, userinfo.getUsername());
			int d=ps.executeUpdate();
			if(d>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(conn);
		}
		return false;
		
		
	}
	
	
	public static void main(String[] args) {
		UserInfoDAO dao=new UserInfoDAO();
		UserInfo userinfo=new UserInfo(1,"qpppp","123456","qq",5000);
		dao.save(userinfo);
		System.out.println(465434);
	}
}
