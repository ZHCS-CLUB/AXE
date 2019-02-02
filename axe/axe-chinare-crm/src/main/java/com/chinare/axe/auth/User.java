package com.chinare.axe.auth;

/**
 * @author 王贵源(kerbores @ gmail.com)
 * @date 2018-11-07 14:23:24
 */
public class User {
    String userName;
    String password;
    String token;

    public User() {}

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
