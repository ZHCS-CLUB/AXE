package com.chinare.axe.auth;

import org.nutz.lang.Lang;
import org.nutz.lang.Strings;

/**
 * @author 王贵源(wangguiyuan@chinarecrm.com.cn)
 */
public class PasswordUtils {

    private PasswordUtils() {

    }

    /**
     * @param  algorithm  摘要算法
     * @param  password   明文密码
     * @param  salt       加密盐
     * @param  iterations 迭代次数
     * @return            密文
     */
    public static String encode(String algorithm, String password, String salt, int iterations) {
        return Lang.digest(algorithm, password.getBytes(), salt.getBytes(), iterations);
    }

    /**
     * @param  password 明文密码
     * @param  salt     加密盐
     * @return          密文
     */
    public static String encode(String password, String salt) {
        return encode("MD5", password, salt, 2);
    }

    /**
     * @param  rawPassword 明文密码
     * @param  salt        盐
     * @param  password    密文
     * @return             是否匹配
     */
    public static boolean check(String rawPassword, String salt, String password) {
        return Strings.equalsIgnoreCase(encode(rawPassword, salt), password);
    }

}
