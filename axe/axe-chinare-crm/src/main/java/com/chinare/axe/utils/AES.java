package com.chinare.axe.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

/**
 * @author 王贵源(kerbores@gmail.com)
 */
public class AES {

    private AES() {}

    public static final String AES_NAME = "AES";
    public static final String CHER_SET = "UTF_8";

    public static final String DEFAULT_KEY = "abcdefghhgfedcba";

    private static Log log = Logs.get();

    /**
     * 使用默认密钥加密
     * 
     * @param sSrc
     *            明文
     * @return 密文
     */
    public static String encrypt(String sSrc) {
        return encrypt(sSrc, DEFAULT_KEY);
    }

    /**
     * 加密
     * 
     * @param sSrc
     *            明文
     * @param sKey
     *            密钥
     * @return 密文
     */
    public static String encrypt(String sSrc, String sKey) {
        try {
            if (sKey == null) {
                log.error("key 不能为空!");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                log.error("key 长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(CHER_SET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_NAME);
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");// "算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(CHER_SET));
            return Base64.encodeToString(encrypted, false);
        }
        catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 解密(使用默认密钥)
     * 
     * @param sSrc
     *            密文
     * @return 明文
     */
    public static String decrypt(String sSrc) {
        return decrypt(sSrc, DEFAULT_KEY);
    }

    /**
     * 解密
     * 
     * @param sSrc
     *            密文
     * @param sKey
     *            密钥
     * @return 明文
     */
    public static String decrypt(String sSrc, String sKey) {
        try {
            if (sKey == null) {
                log.error("key 不能为空!");
                return null;
            }
            if (sKey.length() != 16) {
                log.error("key 长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes(CHER_SET);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_NAME);
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, CHER_SET);
        }
        catch (Exception e) {
            log.error(e);
            return null;
        }
    }

}
