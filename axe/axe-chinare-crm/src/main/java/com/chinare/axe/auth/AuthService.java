package com.chinare.axe.auth;

import java.util.List;

import com.chinare.axe.Result;

/**
 * @author 王贵源( kerbores@gmail.com)
 * @date 2018-11-07 14:36:47
 */
public interface AuthService {

	public static class LoginDto {
		String name;
		String password;

		/**
		 * 
		 */
		public LoginDto() {
			super();
		}

		/**
		 * @param code
		 * @param name
		 * @param password
		 */
		public LoginDto(String name, String password) {
			super();
			this.name = name;
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	public Result login(String user, String password);

	public List<String> roles();

	public List<String> permissions();

	public User user();

	public String token();

	public String userName();

	public User login(LoginDto loginDto);

}