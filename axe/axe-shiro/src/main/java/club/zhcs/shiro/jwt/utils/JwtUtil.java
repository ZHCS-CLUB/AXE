package club.zhcs.shiro.jwt.utils;

import java.util.Date;

import org.nutz.lang.Lang;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author 王贵源<kerbores@gmail.com>
 *
 * @date 2018-12-27 16:20:39
 */
public class JwtUtil {

    private static final long EXPIRE_TIME = 50 * 60 * 1000;

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
        catch (TokenExpiredException exception) {
            throw Lang.makeThrow("令牌已过期");
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
     * 生成签名,50min后过期
     *
     * @param username
     *            用户名
     * @param secret
     *            用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                  .withClaim("username", username)
                  .withExpiresAt(date)
                  .sign(algorithm);

    }
}
