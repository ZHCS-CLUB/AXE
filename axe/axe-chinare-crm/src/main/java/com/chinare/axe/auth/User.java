package com.chinare.axe.auth;

import org.nutz.lang.util.NutMap;

/**
 * @author 王贵源(kerbores @ gmail.com)
 */
public class User {
	String userName;
	String password;
	String token;
	NutMap extInfo = NutMap.NEW();

	public User() {
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	public NutMap getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(NutMap extInfo) {
		this.extInfo = extInfo;
	}

	public String getToken() {
		return token;
	}

	public User addExt(String key, Object value) {
		extInfo.addv(key, value);
		return this;
	}

	public User token(String token) {
		setToken(token);
		return this;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
