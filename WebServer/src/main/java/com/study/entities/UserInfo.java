package com.study.entities;
/**
 * 
 * @描述: UserInfo类
 * @版权: Copyright (c) 2019 
 * @公司: 思迪科技 
 * @作者: 严磊
 * @版本: 1.0 
 * @创建日期: 2019年8月28日 
 * @创建时间: 下午5:00:19
 */
public class UserInfo {
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int account;
	
	public UserInfo(){
		
	}

	public UserInfo(int id, String username, String password, String nickname, int account) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.account = account;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", account=" + account + "]";
	}
	
	
	
}
