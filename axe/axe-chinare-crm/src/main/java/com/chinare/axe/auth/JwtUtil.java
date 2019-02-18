package com.chinare.axe.auth;

import java.util.Date;

import org.nutz.lang.Lang;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 王贵源 (kerbores@gmail.com)
 */
public class JwtUtil {

    /**
     * -一小时的会话
     */
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    /**
     * -一年的会话
     */
    private static final long REMEMBER_EXPIRE_TIME = 60 * 60 * 1000 * 24 * 365;

    /**
     * 校验token是否正确
     * 
     * @param token
     *            密钥
     * @param username
     *            用户名
     * @param secret
     *            密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException exception) {
            throw Lang.makeThrow("token 不合法");
        }
    }

    /**
     * /** -获得token中的信息无需secret解密也能获得
     * 
     * @param token
     *            jwtToken
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        }
        catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @param username
     *            用户名
     * @param secret
     *            用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        return sign(username, secret, EXPIRE_TIME);

    }

    /**
     * @param username
     *            用户名
     * @param secret
     *            密码
     * @param rememberMe
     *            记住我
     * @return jwt token
     */
    public static String sign(String username, String secret, boolean rememberMe) {
        return sign(username, secret, rememberMe ? REMEMBER_EXPIRE_TIME : EXPIRE_TIME);

    }

    /**
     * 生成 token 签名
     * 
     * @param username
     *            用户名
     * @param secret
     *            密码
     * @param expire
     *            过期时间
     * @return 加密的 token
     */
    public static String sign(String username, String secret, long expire) {
        Date date = new Date(System.currentTimeMillis() + expire);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);

    }
}
