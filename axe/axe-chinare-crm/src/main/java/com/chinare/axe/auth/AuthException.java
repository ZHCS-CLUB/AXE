package com.chinare.axe.auth;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    final String status;

    public AuthException() {
        super();
        this.status = "DEFAULT";
    }

    public AuthException(String message) {
        super(message);
        this.status = "DEFAULT";
    }

    public AuthException(String message, String status) {
        super(message);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
