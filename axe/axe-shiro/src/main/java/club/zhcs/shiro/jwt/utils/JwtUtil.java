package club.zhcs.shiro.jwt.utils;

import java.util.Date;

import org.nutz.lang.Lang;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-27 16:20:39
 */
public class JwtUtil {

    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    public static void main(String[] args) {
        System.err.println(sign("18512345678", "d75c0c92f4c502eacf1843e4e0a0b9e4", 1000 * 60 * 5));
    }

    /**
     * 校验token是否正确
     *
     * @param token
     *            密钥
     * @param secret
     *            用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withClaim("username", username)
                                      .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        }
        catch (JWTVerificationException exception) {
            throw Lang.makeThrow("token 不合法");
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
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
     * 生成签名,5min后过期
     *
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
        return JWT.create()
                  .withClaim("username", username)
                  .withExpiresAt(date)
                  .sign(algorithm);

    }
}
