package com.chinare.axe.auth;

import java.util.Date;
import java.util.Map;

import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class JwtUtil {

    private JwtUtil() {}

    /**
     * 一小时的会话
     */
    private static final long EXPIRE_TIME = 60L * 60L * 1000L;

    /**
     * 一年的会话
     */
    private static final long REMEMBER_EXPIRE_TIME = 60L * 60L * 1000L * 24L * 365L;

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
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withSubject(username).build();
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException exception) {
            throw Lang.makeThrow("token 不合法");
        }
    }

    /**
     * 获得token中的用户名信息,无需secret解密也能获得
     * 
     * @param token
     *            jwtToken
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        }
        catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得Claim中的用户名信息,无需secret解密也能获得
     * 
     * @param token
     *            token
     * @return Claims
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        }
        catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 签名生成token
     * 
     * @param username
     *            用户名
     * @param secret
     *            密钥
     * @return token串
     */
    public static String sign(String username, String secret) {
        return sign(username, secret, EXPIRE_TIME);

    }

    /**
     * 签名生成token
     * 
     * @param username
     *            用户名
     * @param secret
     *            密钥
     * @param rememberMe
     *            记住标识
     * @return token串
     */
    public static String sign(String username, String secret, boolean rememberMe) {
        return sign(username, secret, rememberMe ? REMEMBER_EXPIRE_TIME : EXPIRE_TIME);

    }

    /**
     * 签名生成token
     * 
     * @param username
     *            用户名
     * @param secret
     *            密钥
     * @param expire
     *            token过期时长(秒)
     * @return token串
     */
    public static String sign(String username, String secret, long expire) {
        Date date = new Date(System.currentTimeMillis() + expire);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withSubject(username).withExpiresAt(date).sign(algorithm);

    }

    /**
     * 签名生成token
     * 
     * @param username
     *            用户名
     * @param secret
     *            密钥
     * @param expire
     *            token过期时长(秒)
     * @param claims
     *            明文信息
     * @return token串
     */
    public static String sign(String username, String secret, long expire, NutMap claims) {
        Date date = new Date(System.currentTimeMillis() + expire);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Builder builder = JWT.create().withSubject(username).withExpiresAt(date);
        claims.keySet().stream().forEach(key -> {
            builder.withClaim(key, claims.getAs(key, String.class));
        });
        return builder.sign(algorithm);

    }
}
