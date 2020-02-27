package com.chinare.axe.auth;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class PasswordUtils {

    private PasswordUtils() {

    }

    /**
     * 加密
     * 
     * @param algorithm
     *            算法
     * @param password
     *            明文密码
     * @param salt
     *            加密盐
     * @param iterations
     *            加密迭代次数
     * @return 密文
     */
    public static String encode(String algorithm, String password, String salt, int iterations) {
        return Lang.digest(algorithm, password.getBytes(), salt.getBytes(), iterations);
    }

    /**
     * 使用md5迭代两次加密
     * 
     * @param password
     *            明文密码
     * @param salt
     *            加密盐
     * @return 密文
     */
    public static String encode(String password, String salt) {
        return encode("MD5", password, salt, 2);
    }

    /**
     * 密文匹配检查,检测使用md5迭代两次加密的密文是否匹配
     * 
     * @param rawPassword
     *            明文密码
     * @param salt
     *            加密盐
     * @param password
     *            密文密码
     * @return 是否匹配
     */
    public static boolean check(String rawPassword, String salt, String password) {
        return Strings.equalsIgnoreCase(encode(rawPassword, salt), password);
    }
}
